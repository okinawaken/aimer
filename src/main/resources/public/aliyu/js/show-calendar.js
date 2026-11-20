/**
 * 演出日历：按月读取有演出的日期，选中日期再读取当天的项目列表。
 * 上游只返回有演出的日期，本月其余格子在前端补齐；今天按 /content/current-timestamp 校准。
 */

import { api, tplUrl } from './aliyu-api.js';
import { $, $$ } from './aliyu-ui.js';

const WEEKDAYS = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
const ICON_ALERT = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6"'
    + ' stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">'
    + '<circle cx="12" cy="12" r="9"/><path d="M12 7v6"/><path d="M12 16.5h.01"/></svg>';
const ICON_CALENDAR = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6"'
    + ' stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">'
    + '<rect x="3.5" y="5" width="17" height="15" rx="2"/>'
    + '<path d="M3.5 9.5h17"/><path d="M8 3v4"/><path d="M16 3v4"/><path d="M9 14h6"/></svg>';

const state = {
    today: startOfDay(new Date()),
    viewYear: 0,
    viewMonth: 0,
    selectedKey: '',
    selectedDate: null,
    calendar: new Map(),
    monthSeq: 0,
    daySeq: 0
};

const monthLabel = $('#monthLabel');
const prevBtn = $('#btnPrevMonth');
const nextBtn = $('#btnNextMonth');
const calGrid = $('#calGrid');
const calEmpty = $('#calEmpty');
const calError = $('#calError');
const monthSummary = $('#monthSummary');
const dayTitle = $('#dayTitle');
const daySub = $('#daySub');
const dayBody = $('#dayBody');
const liveStatus = $('#liveStatus');

/* ---------------------------------------------------------------- 日期工具 */

function pad2(value) {
    return String(value).padStart(2, '0');
}

function startOfDay(date) {
    return new Date(date.getFullYear(), date.getMonth(), date.getDate());
}

function keyOf(date) {
    return date.getFullYear() + '-' + pad2(date.getMonth() + 1) + '-' + pad2(date.getDate());
}

function slashOf(date) {
    return date.getFullYear() + '/' + pad2(date.getMonth() + 1) + '/' + pad2(date.getDate());
}

function monthFirst(year, month) {
    return new Date(year, month, 1);
}

function monthLast(year, month) {
    return new Date(year, month + 1, 0);
}

function daysInMonth(year, month) {
    return monthLast(year, month).getDate();
}

function mondayIndex(date) {
    return (date.getDay() + 6) % 7;
}

function weekdayName(date) {
    return WEEKDAYS[mondayIndex(date)];
}

function isSameDay(a, b) {
    return a.getFullYear() === b.getFullYear()
        && a.getMonth() === b.getMonth()
        && a.getDate() === b.getDate();
}

function monthLabelText(year, month) {
    return year + ' 年 ' + (month + 1) + ' 月';
}

function dayTitleText(date) {
    return (date.getMonth() + 1) + ' 月 ' + date.getDate() + ' 日';
}

/**
 * 解析上游日期：yyyy/MM/dd、yyyy-MM-dd、带时间的字符串或毫秒/秒时间戳。
 * @param {*} value 上游值
 * @returns {Date|null} 本地零点日期
 */
function parseApiDate(value) {
    if (value === null || value === undefined || value === '') {
        return null;
    }
    if (typeof value === 'number' && Number.isFinite(value)) {
        return fromEpoch(value);
    }
    const text = String(value).trim();
    if (!text) {
        return null;
    }
    if (/^\d{9,14}$/.test(text)) {
        const fromNumber = fromEpoch(Number(text));
        if (fromNumber) {
            return fromNumber;
        }
    }
    const match = text.match(/^(\d{4})[-/年](\d{1,2})[-/月](\d{1,2})/);
    if (match) {
        const year = Number(match[1]);
        const month = Number(match[2]) - 1;
        const day = Number(match[3]);
        const date = new Date(year, month, day);
        if (date.getFullYear() === year && date.getMonth() === month && date.getDate() === day) {
            return date;
        }
    }
    const fallback = new Date(text.replace(/-/g, '/'));
    return Number.isNaN(fallback.getTime()) ? null : startOfDay(fallback);
}

function fromEpoch(value) {
    const millis = value < 1e12 ? value * 1000 : value;
    const date = new Date(millis);
    return Number.isNaN(date.getTime()) ? null : startOfDay(date);
}

/**
 * 解析 /content/current-timestamp 返回的字符串；无法解析时返回 null，由调用方回退本地时间。
 * @param {*} raw 上游值
 * @returns {Date|null} 日期
 */
function parseServerTimestamp(raw) {
    if (raw === null || raw === undefined) {
        return null;
    }
    const text = String(raw).trim();
    if (!text) {
        return null;
    }
    if (/^\d+$/.test(text)) {
        return fromEpoch(Number(text));
    }
    const date = parseApiDate(text);
    if (date) {
        return date;
    }
    const fallback = new Date(text.replace(/-/g, '/'));
    return Number.isNaN(fallback.getTime()) ? null : startOfDay(fallback);
}

/* ---------------------------------------------------------------- 值工具 */

function el(tag, className, text) {
    const node = document.createElement(tag);
    if (className) {
        node.className = className;
    }
    if (text !== undefined && text !== null) {
        node.textContent = text;
    }
    return node;
}

function truthy(value) {
    return value === true || value === 1 || value === '1' || value === 'true';
}

function toNumber(value) {
    if (value === null || value === undefined || value === '') {
        return null;
    }
    const number = Number(value);
    return Number.isFinite(number) ? number : null;
}

function firstText(...values) {
    for (const value of values) {
        if (value === null || value === undefined) {
            continue;
        }
        const text = String(value).trim();
        if (text) {
            return text;
        }
    }
    return '';
}

function imageUrl(value) {
    const text = String(value || '').trim();
    if (!text) {
        return '';
    }
    return text.startsWith('//') ? location.protocol + text : text;
}

function priceText(value) {
    const text = String(value === null || value === undefined ? '' : value).trim();
    if (!text) {
        return '';
    }
    if (text.includes('起')) {
        return text;
    }
    if (text.includes('¥') || text.includes('元')) {
        return text + ' 起';
    }
    return '¥' + text + ' 起';
}

function clock(date) {
    return pad2(date.getHours()) + ':' + pad2(date.getMinutes());
}

function timeRangeText(start, end) {
    const startMs = toNumber(start);
    const endMs = toNumber(end);
    const startDate = startMs === null ? null : new Date(startMs);
    const endDate = endMs === null ? null : new Date(endMs);
    const validStart = startDate && !Number.isNaN(startDate.getTime());
    const validEnd = endDate && !Number.isNaN(endDate.getTime());

    if (!validStart && !validEnd) {
        return '';
    }
    if (!validStart) {
        return '结束 ' + clock(endDate);
    }
    if (!validEnd) {
        return clock(startDate);
    }
    if (isSameDay(startDate, endDate)) {
        return clock(startDate) + '–' + clock(endDate);
    }
    return (startDate.getMonth() + 1) + ' 月 ' + startDate.getDate() + ' 日 ' + clock(startDate)
        + '–' + (endDate.getMonth() + 1) + ' 月 ' + endDate.getDate() + ' 日 ' + clock(endDate);
}

/**
 * 从列表项推导售票状态；没有匹配时返回 null，不臆造状态。
 * @param {object} item 项目
 * @returns {{text: string, solid: boolean}|null} 状态
 */
function statusOf(item) {
    if (truthy(item.sellOut)) {
        return { text: '售罄', solid: true };
    }
    if (toNumber(item.saleState) === 3) {
        return { text: '已结束', solid: false };
    }
    const days = toNumber(item.countDownShowDay);
    if (toNumber(item.countDownState) === 1) {
        return {
            text: days !== null && days >= 1 ? days + ' 天后开售' : '即将开售',
            solid: false
        };
    }
    if (days !== null && days >= 1) {
        return { text: days + ' 天后开售', solid: false };
    }
    return null;
}

function announce(text) {
    liveStatus.textContent = text;
}

/* ---------------------------------------------------------------- 页头 */

function updateMonthChrome() {
    const year = state.viewYear;
    const month = state.viewMonth;
    monthLabel.textContent = monthLabelText(year, month);

    const prev = new Date(year, month - 1, 1);
    const next = new Date(year, month + 1, 1);
    prevBtn.setAttribute('aria-label', '上一月，' + monthLabelText(prev.getFullYear(), prev.getMonth()));
    nextBtn.setAttribute('aria-label', '下一月，' + monthLabelText(next.getFullYear(), next.getMonth()));
}

function changeMonth(delta) {
    const target = new Date(state.viewYear, state.viewMonth + delta, 1);
    state.viewYear = target.getFullYear();
    state.viewMonth = target.getMonth();
    loadMonth();
}

/* ---------------------------------------------------------------- 日历 */

function renderCalendarSkeleton() {
    calGrid.replaceChildren();
    calGrid.classList.add('is-loading');
    calGrid.setAttribute('aria-busy', 'true');
    for (let index = 0; index < 35; index++) {
        const cell = el('div', 'cal-skel skeleton');
        cell.setAttribute('aria-hidden', 'true');
        calGrid.appendChild(cell);
    }
    calEmpty.hidden = true;
    calError.hidden = true;
    calError.replaceChildren();
}

function renderCalendarError(message) {
    calGrid.replaceChildren();
    calGrid.classList.remove('is-loading');
    calGrid.setAttribute('aria-busy', 'false');
    calEmpty.hidden = true;
    calError.hidden = false;
    calError.replaceChildren();
    calError.appendChild(el('p', 'cal-error__title', '日历读取失败'));
    calError.appendChild(el('p', 'cal-error__text', message || '稍后重试。'));
    const retry = el('button', 'btn btn--secondary', '重新读取日历');
    retry.type = 'button';
    retry.addEventListener('click', () => loadMonth());
    calError.appendChild(retry);
}

function dayAriaLabel(date, isToday, hasShow, soldOut) {
    let text = (date.getMonth() + 1) + ' 月 ' + date.getDate() + ' 日';
    if (isToday) {
        text += '，今天';
    }
    if (hasShow) {
        text += soldOut ? '，有演出，已售罄' : '，有演出';
    }
    return text;
}

function renderCalendar(selectedDate) {
    const year = state.viewYear;
    const month = state.viewMonth;
    const todayKey = keyOf(state.today);
    const selectedKey = selectedDate ? keyOf(selectedDate) : '';

    calGrid.replaceChildren();
    calGrid.classList.remove('is-loading');
    calGrid.setAttribute('aria-busy', 'false');
    calGrid.setAttribute('aria-label', monthLabelText(year, month) + '演出日历');
    calError.hidden = true;

    const lead = mondayIndex(monthFirst(year, month));
    for (let index = 0; index < lead; index++) {
        const blank = el('div', 'cal-day cal-day--blank');
        blank.setAttribute('aria-hidden', 'true');
        calGrid.appendChild(blank);
    }

    const total = daysInMonth(year, month);
    for (let day = 1; day <= total; day++) {
        const date = new Date(year, month, day);
        const key = keyOf(date);
        const info = state.calendar.get(key);
        const isToday = key === todayKey;
        const isSelected = key === selectedKey;
        const hasShow = Boolean(info);
        const soldOut = hasShow && info.sellOut;

        const button = el('button', 'cal-day');
        button.type = 'button';
        button.dataset.key = key;
        button.dataset.day = String(day);
        button.classList.toggle('has-show', hasShow);
        button.classList.toggle('is-soldout', soldOut);
        button.classList.toggle('is-today', isToday);
        button.classList.toggle('is-selected', isSelected);
        button.tabIndex = isSelected ? 0 : -1;
        button.setAttribute('aria-pressed', String(isSelected));
        if (isToday) {
            button.setAttribute('aria-current', 'date');
        }
        button.setAttribute('aria-label', dayAriaLabel(date, isToday, hasShow, soldOut));
        button.appendChild(el('span', 'cal-num', String(day)));

        const mark = el('span', 'cal-mark');
        mark.setAttribute('aria-hidden', 'true');
        button.appendChild(mark);

        button.addEventListener('click', () => selectDate(date));
        calGrid.appendChild(button);
    }

    const trailing = (7 - ((lead + total) % 7)) % 7;
    for (let index = 0; index < trailing; index++) {
        const blank = el('div', 'cal-day cal-day--blank');
        blank.setAttribute('aria-hidden', 'true');
        calGrid.appendChild(blank);
    }
}

function updateSelectionUI() {
    $$('.cal-day[data-day]', calGrid).forEach(button => {
        const selected = button.dataset.key === state.selectedKey;
        button.classList.toggle('is-selected', selected);
        button.setAttribute('aria-pressed', String(selected));
        button.tabIndex = selected ? 0 : -1;
    });
}

function selectDate(date) {
    const key = keyOf(date);
    if (state.selectedKey === key) {
        return;
    }
    state.selectedKey = key;
    state.selectedDate = date;
    updateSelectionUI();
    loadDay(date);
}

function onCalendarKeydown(event) {
    const button = event.target.closest('.cal-day[data-day]');
    if (!button) {
        return;
    }
    let delta = 0;
    if (event.key === 'ArrowLeft') {
        delta = -1;
    } else if (event.key === 'ArrowRight') {
        delta = 1;
    } else if (event.key === 'ArrowUp') {
        delta = -7;
    } else if (event.key === 'ArrowDown') {
        delta = 7;
    } else {
        return;
    }
    event.preventDefault();

    const current = Number(button.dataset.day);
    const max = daysInMonth(state.viewYear, state.viewMonth);
    const next = Math.min(max, Math.max(1, current + delta));
    if (next === current) {
        return;
    }
    const target = calGrid.querySelector('.cal-day[data-day="' + next + '"]');
    if (!target) {
        return;
    }
    $$('.cal-day[data-day]', calGrid).forEach(item => {
        item.tabIndex = item === target ? 0 : -1;
    });
    target.focus();
}

function chooseSelection(keys) {
    const todayKey = keyOf(state.today);
    const currentMonth = state.today.getFullYear() === state.viewYear
        && state.today.getMonth() === state.viewMonth;

    if (currentMonth && state.calendar.has(todayKey)) {
        return state.today;
    }
    if (keys.length) {
        const parts = keys[0].split('-').map(Number);
        return new Date(parts[0], parts[1] - 1, parts[2]);
    }
    if (currentMonth) {
        return state.today;
    }
    return monthFirst(state.viewYear, state.viewMonth);
}

async function loadMonth() {
    const seq = ++state.monthSeq;
    state.daySeq += 1;
    state.calendar = new Map();
    state.selectedKey = '';
    state.selectedDate = null;

    const year = state.viewYear;
    const month = state.viewMonth;
    updateMonthChrome();
    monthSummary.textContent = '读取中';
    monthSummary.classList.remove('is-error');
    renderCalendarSkeleton();
    renderDaySkeleton();
    dayTitle.textContent = '选中日期';
    daySub.textContent = '读取中';
    announce(monthLabelText(year, month) + '，正在读取');

    try {
        const list = await api('project/show-calendar', {
            query: {
                startDate: keyOf(monthFirst(year, month)),
                endDate: keyOf(monthLast(year, month))
            }
        });
        if (seq !== state.monthSeq) {
            return;
        }

        const prefix = year + '-' + pad2(month + 1) + '-';
        const calendar = new Map();
        (Array.isArray(list) ? list : []).forEach(item => {
            const date = parseApiDate(item && item.date);
            if (!date) {
                return;
            }
            const key = keyOf(date);
            if (!key.startsWith(prefix)) {
                return;
            }
            calendar.set(key, { date, sellOut: truthy(item.sellOut) });
        });

        state.calendar = calendar;
        const keys = [...calendar.keys()].sort();
        const soldOut = [...calendar.values()].filter(item => item.sellOut).length;

        if (keys.length) {
            monthSummary.textContent = '当月 ' + keys.length + ' 天有演出 · ' + soldOut + ' 天售罄';
            monthSummary.classList.remove('is-error');
        } else {
            monthSummary.textContent = '当月暂无演出';
            calEmpty.hidden = false;
        }

        const selected = chooseSelection(keys);
        state.selectedKey = keyOf(selected);
        state.selectedDate = selected;
        renderCalendar(selected);
        announce(monthLabelText(year, month) + '，'
            + (keys.length ? '共 ' + keys.length + ' 天有演出，' + soldOut + ' 天售罄' : '没有演出'));
        loadDay(selected);
    } catch (error) {
        if (seq !== state.monthSeq) {
            return;
        }
        monthSummary.textContent = '读取失败';
        monthSummary.classList.add('is-error');
        renderCalendarError(error.message);
        renderDayMessage('日历读取失败', '先重新读取日历，再选择日期。', () => loadMonth(), '重新读取日历');
        dayTitle.textContent = '选中日期';
        daySub.textContent = '读取失败';
        announce('日历读取失败：' + error.message);
    }
}

/* ---------------------------------------------------------------- 当天场次 */

function renderDaySkeleton() {
    dayBody.replaceChildren();
    dayBody.setAttribute('aria-busy', 'true');
    for (let index = 0; index < 2; index++) {
        const row = el('div', 'day-skel');
        row.appendChild(el('div', 'skeleton day-skel__media'));
        const lines = el('div', 'day-skel__lines');
        lines.appendChild(el('div', 'skeleton skeleton--line'));
        lines.appendChild(el('div', 'skeleton skeleton--line'));
        lines.appendChild(el('div', 'skeleton skeleton--line'));
        row.appendChild(lines);
        dayBody.appendChild(row);
    }
    playDayTransition();
}

function renderDayMessage(title, text, retryFn, retryLabel) {
    dayBody.replaceChildren();
    dayBody.setAttribute('aria-busy', 'false');
    const empty = el('div', 'empty');
    const icon = el('div', 'empty__icon');
    icon.innerHTML = ICON_ALERT;
    empty.appendChild(icon);
    empty.appendChild(el('h3', '', title));
    empty.appendChild(el('p', '', text));
    const retry = el('button', 'btn btn--secondary', retryLabel || '重试');
    retry.type = 'button';
    retry.addEventListener('click', retryFn);
    empty.appendChild(retry);
    dayBody.appendChild(empty);
    playDayTransition();
}

function playDayTransition() {
    dayBody.classList.remove('is-swap');
    void dayBody.offsetWidth;
    dayBody.classList.add('is-swap');
}

function dayItem(item) {
    const name = firstText(item.projectName, item.aliasName) || '未命名演出';
    const token = firstText(item.projectToken);
    const link = el('a', 'day-item');
    link.href = tplUrl('show-detail', { projectToken: token });

    const media = el('div', 'day-item__media m-zoom');
    const placeholder = el('span', 'day-item__ph', name.slice(0, 1));
    placeholder.setAttribute('aria-hidden', 'true');
    media.appendChild(placeholder);
    const url = imageUrl(item.imgUrl);
    if (url) {
        const img = document.createElement('img');
        img.src = url;
        img.alt = '';
        img.width = 600;
        img.height = 800;
        img.loading = 'lazy';
        img.decoding = 'async';
        img.addEventListener('error', () => img.remove());
        media.appendChild(img);
    }

    const body = el('div', 'day-item__body');
    const top = el('div', 'day-item__top');
    top.appendChild(el('h3', 'day-item__title', name));
    const status = statusOf(item);
    if (status) {
        top.appendChild(el('span', 'chip' + (status.solid ? ' chip--solid' : ''), status.text));
    }
    body.appendChild(top);

    const place = [firstText(item.cityName), firstText(item.siteName)].filter(Boolean).join(' · ');
    if (place) {
        body.appendChild(el('p', 'day-item__meta', place));
    }
    const time = timeRangeText(item.startTime, item.endTime);
    if (time) {
        body.appendChild(el('p', 'day-item__time', time));
    }

    const foot = el('div', 'day-item__foot');
    const price = priceText(item.minPrice);
    if (price) {
        foot.appendChild(el('span', 'day-item__price', price));
    } else {
        foot.appendChild(el('span', 'day-item__price day-item__price--none', '票价见详情'));
    }
    foot.appendChild(el('span', 'day-item__more', '查看详情'));
    body.appendChild(foot);

    link.appendChild(media);
    link.appendChild(body);
    return link;
}

function renderDayList(items) {
    dayBody.replaceChildren();
    if (!items.length) {
        const empty = el('div', 'empty');
        const icon = el('div', 'empty__icon');
        icon.innerHTML = ICON_CALENDAR;
        empty.appendChild(icon);
        empty.appendChild(el('h3', '', '这一天没有演出。'));
        empty.appendChild(el('p', '', '换一天，或看看本月其他日期。'));
        dayBody.appendChild(empty);
    } else {
        const list = el('div', 'day-list');
        items.forEach(item => list.appendChild(dayItem(item)));
        dayBody.appendChild(list);
    }
    playDayTransition();
}

async function loadDay(date) {
    const seq = ++state.daySeq;
    const slash = slashOf(date);
    const today = isSameDay(date, state.today);

    dayBody.setAttribute('aria-busy', 'true');
    dayTitle.textContent = dayTitleText(date);
    daySub.textContent = (today ? '今天 · ' : weekdayName(date) + ' · ') + '读取中';
    renderDaySkeleton();

    try {
        const data = await api('project/list', {
            query: {
                startTime: slash,
                endTime: slash,
                pageSize: 20
            }
        });
        if (seq !== state.daySeq) {
            return;
        }

        const items = data && Array.isArray(data.dataList) ? data.dataList : [];
        const totalRow = data ? toNumber(data.totalRow) : null;
        const total = totalRow !== null && totalRow > 0 ? totalRow : items.length;

        dayBody.setAttribute('aria-busy', 'false');
        const parts = [];
        if (today) {
            parts.push('今天');
        }
        parts.push(weekdayName(date));
        if (total > 0) {
            parts.push(total > items.length
                ? '共 ' + total + ' 个演出，显示前 ' + items.length + ' 个'
                : '共 ' + total + ' 个演出');
        } else {
            parts.push('没有演出');
        }
        daySub.textContent = parts.join(' · ');
        renderDayList(items);
        announce(dayTitleText(date) + '，' + (total > 0 ? '共 ' + total + ' 个演出' : '没有演出'));
    } catch (error) {
        if (seq !== state.daySeq) {
            return;
        }
        dayBody.setAttribute('aria-busy', 'false');
        daySub.textContent = weekdayName(date) + ' · 读取失败';
        renderDayMessage('场次读取失败', error.message || '稍后重试。', () => loadDay(date), '重试这一天的场次');
        announce(dayTitleText(date) + '，场次读取失败');
    }
}

/* ---------------------------------------------------------------- 启动 */

async function calibrateToday() {
    try {
        const raw = await api('content/current-timestamp');
        const parsed = parseServerTimestamp(raw);
        if (parsed) {
            state.today = startOfDay(parsed);
        }
    } catch (error) {
        // 上游时间不可用时按本地时间
    }
}

async function boot() {
    await calibrateToday();
    state.viewYear = state.today.getFullYear();
    state.viewMonth = state.today.getMonth();
    updateMonthChrome();
    loadMonth();
}

prevBtn.addEventListener('click', () => changeMonth(-1));
nextBtn.addEventListener('click', () => changeMonth(1));
calGrid.addEventListener('keydown', onCalendarKeydown);

boot();
