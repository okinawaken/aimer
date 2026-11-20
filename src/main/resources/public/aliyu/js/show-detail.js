/**
 * 阿里鱼演出详情页：读取项目详情、按日期切换场次、设置开售提醒、渲染热门演出。
 * 上游富文本一律经过白名单清洗后再插入页面，其余字符串全部使用 textContent。
 */
import { api, tplUrl } from './aliyu-api.js';
import { $, $$, toast, setBusy } from './aliyu-ui.js';

const PROJECT_TOKEN = (new URLSearchParams(location.search).get('projectToken') || '').trim();
const HOT_LIMIT = 4;
const WEEKDAYS = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
const SHOWS_URL = tplUrl('shows');

const state = {
    projectToken: PROJECT_TOKEN,
    detail: null,
    events: [],
    dates: [],
    byDate: new Map(),
    date: '',
    selectedEvent: null,
    countdownTimer: 0,
    seq: 0,
    hotSeq: 0
};

const detailHead = $('#detailHead');
const heroLoading = $('#heroLoading');
const heroBody = $('#heroBody');
const detailTitle = $('#detailTitle');
const detailLoc = $('#detailLoc');
const detailDate = $('#detailDate');
const detailPrice = $('#detailPrice');
const detailPriceDesc = $('#detailPriceDesc');
const detailSale = $('#detailSale');
const detailSaleText = $('#detailSaleText');
const detailPoster = $('#detailPoster');
const pageStatus = $('#pageStatus');
const loadingSection = $('#loadingSection');
const stateSection = $('#stateSection');
const stateCard = $('#stateCard');
const sessionsSection = $('#sessions');
const sessionsStatus = $('#sessionsStatus');
const dateStrip = $('#dateStrip');
const eventList = $('#eventList');
const summaryState = $('#summaryState');
const summaryBody = $('#summaryBody');
const buyBtn = $('#buyBtn');
const remindBtn = $('#remindBtn');
const remindStatus = $('#remindStatus');
const introSection = $('#intro');
const richDetail = $('#richDetail');
const introFactsCard = $('#introFactsCard');
const introRows = $('#introRows');
const venueSection = $('#venue');
const venueCard = $('#venueCard');
const venueRows = $('#venueRows');
const noticeCard = $('#noticeCard');
const noticeBody = $('#noticeBody');
const hotSection = $('#hot');
const hotGrid = $('#hotGrid');
const hotStatus = $('#hotStatus');
const summaryCard = $('#summaryCard');
const actionBar = $('#actionBar');
const actionBarTitle = $('#actionBarTitle');
const actionBarMeta = $('#actionBarMeta');
const actionBarActions = $('#actionBarActions');
const compactQuery = window.matchMedia('(max-width: 1023px)');

const DETAIL_SECTIONS = [sessionsSection, introSection, venueSection, hotSection];

/* ---------------------------------------------------------------- 通用工具 */

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

function nonEmpty(value) {
    return value !== null && value !== undefined && String(value).trim() !== '';
}

function pad2(value) {
    return String(value).padStart(2, '0');
}

function parseDate(value) {
    if (value === null || value === undefined || value === '') {
        return null;
    }
    if (value instanceof Date) {
        return Number.isNaN(value.getTime()) ? null : value;
    }
    if (typeof value === 'number') {
        const fromNumber = new Date(value);
        return Number.isNaN(fromNumber.getTime()) ? null : fromNumber;
    }
    const text = String(value).trim();
    if (!text) {
        return null;
    }
    if (/^\d{10,13}$/.test(text)) {
        const fromMillis = new Date(Number(text));
        return Number.isNaN(fromMillis.getTime()) ? null : fromMillis;
    }
    let date = new Date(text);
    if (Number.isNaN(date.getTime())) {
        date = new Date(text.replace(/-/g, '/'));
    }
    return Number.isNaN(date.getTime()) ? null : date;
}

function ymd(value) {
    const date = parseDate(value);
    if (!date) {
        return '';
    }
    return date.getFullYear() + '-' + pad2(date.getMonth() + 1) + '-' + pad2(date.getDate());
}

function formatClock(value) {
    const date = parseDate(value);
    if (!date) {
        return '';
    }
    return pad2(date.getHours()) + ':' + pad2(date.getMinutes());
}

function formatMonthDay(value) {
    const date = parseDate(value);
    if (!date) {
        return '日期待定';
    }
    return (date.getMonth() + 1) + '月' + date.getDate() + '日';
}

function formatWeekday(value) {
    const date = parseDate(value);
    return date ? WEEKDAYS[date.getDay()] : '';
}

function formatFullDate(value) {
    const date = parseDate(value);
    if (!date) {
        return '时间待定';
    }
    return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日 ' + WEEKDAYS[date.getDay()];
}

function formatRange(start, end) {
    const from = parseDate(start);
    const to = parseDate(end);
    const one = date => date.getFullYear() + '.' + pad2(date.getMonth() + 1) + '.' + pad2(date.getDate());
    if (from && to && ymd(from) !== ymd(to)) {
        return one(from) + ' – ' + one(to);
    }
    if (from) {
        return one(from);
    }
    return to ? one(to) : '';
}

function formatRemaining(millis) {
    const total = Math.max(0, Math.floor(millis / 1000));
    const days = Math.floor(total / 86400);
    const hours = Math.floor((total % 86400) / 3600);
    const minutes = Math.floor((total % 3600) / 60);
    const seconds = total % 60;
    const clock = pad2(hours) + ':' + pad2(minutes) + ':' + pad2(seconds);
    return days > 0 ? days + ' 天 ' + clock : clock;
}

function isCount(value) {
    if (value === null || value === undefined || value === '') {
        return false;
    }
    const number = Number(value);
    return Number.isFinite(number) && number > 0;
}

function kvRow(label, value) {
    const row = el('div', 'kv-row');
    row.appendChild(el('span', 'k', label));
    row.appendChild(el('span', 'v', value));
    return row;
}

function priceRange(event) {
    const list = event && Array.isArray(event.priceVoList) ? event.priceVoList : [];
    const numbers = [];
    let first = '';
    list.forEach(item => {
        if (!item || !nonEmpty(item.price)) {
            return;
        }
        const raw = String(item.price).trim();
        if (!first) {
            first = raw;
        }
        const cleaned = raw.replace(/[^\d.]/g, '');
        const number = Number(cleaned);
        if (cleaned && Number.isFinite(number)) {
            numbers.push(number);
        }
    });
    if (numbers.length) {
        const min = Math.min(...numbers);
        const max = Math.max(...numbers);
        const format = number => Number.isInteger(number) ? String(number) : number.toFixed(2);
        return min === max ? '¥' + format(min) : '¥' + format(min) + ' – ¥' + format(max);
    }
    if (first) {
        return first.startsWith('¥') ? first : '¥' + first;
    }
    return '价格见凭证';
}

function eventStatus(event, project) {
    if (Number(project && project.saleState) === 3) {
        return { text: '演出已结束', className: 'chip--quiet' };
    }
    if (event.sellOut === true) {
        return { text: '已售罄', className: 'chip--alert' };
    }
    if (Number(event.saleState) === 3) {
        return { text: '暂不可售', className: 'chip--quiet' };
    }
    if (Number(event.saleRegisterState) === 1) {
        return { text: '已预约', className: 'chip--on' };
    }
    if (Number(event.registerEnableState) === 1) {
        return { text: '可预约', className: 'chip--on' };
    }
    if (Number(event.saleState) === 1 || Number(event.countDownState) === 1) {
        return { text: '即将开售', className: 'chip--warn' };
    }
    return { text: '售票中', className: 'chip--on' };
}

function heroStatus(detail) {
    if (Number(detail.saleState) === 3) {
        return { text: '已结束', kind: 'error' };
    }
    if (Number(detail.countDownState) === 1) {
        return { text: '即将开售', kind: '' };
    }
    return { text: '售票中', kind: 'on' };
}

function iconNode(paths) {
    const icon = el('div', 'empty__icon');
    icon.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" '
        + 'stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">' + paths + '</svg>';
    return icon;
}

/* ---------------------------------------------------------------- 富文本白名单清洗 */

const ALLOWED_TAGS = new Set(['P', 'BR', 'STRONG', 'EM', 'B', 'I', 'UL', 'OL', 'LI', 'H3', 'H4', 'A', 'IMG']);
const DROP_TAGS = new Set(['SCRIPT', 'STYLE', 'IFRAME', 'OBJECT', 'SVG', 'EMBED', 'LINK', 'META',
    'TEMPLATE', 'NOSCRIPT', 'CANVAS', 'MATH', 'VIDEO', 'AUDIO', 'SOURCE', 'TRACK', 'BASE', 'TITLE']);

function safeUrl(value) {
    if (!nonEmpty(value)) {
        return '';
    }
    try {
        const url = new URL(String(value).trim(), document.baseURI);
        if (url.protocol === 'http:' || url.protocol === 'https:') {
            return url.href;
        }
    } catch (e) {
        return '';
    }
    return '';
}

function sanitizeHtml(html) {
    const parsed = new DOMParser().parseFromString(String(html || ''), 'text/html');
    const output = document.createDocumentFragment();
    const copyChildren = (source, target) => {
        source.childNodes.forEach(node => {
            if (node.nodeType === Node.TEXT_NODE) {
                target.appendChild(document.createTextNode(node.nodeValue));
                return;
            }
            if (node.nodeType !== Node.ELEMENT_NODE) {
                return;
            }
            const tag = node.tagName.toUpperCase();
            if (DROP_TAGS.has(tag)) {
                return;
            }
            if (!ALLOWED_TAGS.has(tag)) {
                copyChildren(node, target);
                return;
            }
            if (tag === 'A') {
                const href = safeUrl(node.getAttribute('href'));
                if (!href) {
                    copyChildren(node, target);
                    return;
                }
                const link = el('a');
                link.setAttribute('href', href);
                link.setAttribute('target', '_blank');
                link.setAttribute('rel', 'noopener noreferrer');
                copyChildren(node, link);
                target.appendChild(link);
                return;
            }
            if (tag === 'IMG') {
                const src = safeUrl(node.getAttribute('src'));
                if (!src) {
                    return;
                }
                const image = el('img');
                image.setAttribute('src', src);
                image.setAttribute('alt', nonEmpty(node.getAttribute('alt')) ? String(node.getAttribute('alt')).slice(0, 160) : '演出介绍图片');
                image.setAttribute('loading', 'lazy');
                image.setAttribute('decoding', 'async');
                image.setAttribute('referrerpolicy', 'no-referrer');
                target.appendChild(image);
                return;
            }
            const clean = document.createElement(tag.toLowerCase());
            copyChildren(node, clean);
            target.appendChild(clean);
        });
    };
    copyChildren(parsed.body, output);
    return output;
}

/* ---------------------------------------------------------------- 状态与骨架 */

function setPageStatus(message) {
    pageStatus.textContent = message;
}

function setSessionsStatus(message) {
    sessionsStatus.textContent = message || '';
}

function renderHeroSkeleton() {
    heroLoading.hidden = false;
    heroBody.hidden = true;
    detailPoster.replaceChildren(el('div', 'skeleton detail-poster__skeleton'));
    detailPoster.classList.remove('is-ready');
}

function renderStateCard(titleText, message, action) {
    detailHead.hidden = true;
    loadingSection.hidden = true;
    DETAIL_SECTIONS.forEach(section => {
        section.hidden = true;
    });
    stateSection.hidden = false;

    const empty = el('div', 'empty');
    empty.appendChild(iconNode('<path d="M4 5h16v14H4z"/><path d="M4 9h16"/><path d="M8 5v4"/>'));
    const title = el('h1', '', titleText);
    title.id = 'state-h';
    empty.appendChild(title);
    empty.appendChild(el('p', '', message));
    if (action) {
        empty.appendChild(action);
    }
    stateCard.replaceChildren(empty);
}

function renderNoToken() {
    const link = el('a', 'btn btn--primary', '打开演出列表');
    link.href = SHOWS_URL;
    renderStateCard(
        '从演出列表选一场',
        '还没有选择演出。先回到演出列表，点开一场演出，再查看场次、票价与开售提醒。',
        link
    );
    setPageStatus('未选择演出');
}

function renderError(message) {
    const actions = el('div', 'btn-row');
    const retry = el('button', 'btn btn--secondary', '重新加载');
    retry.type = 'button';
    retry.addEventListener('click', () => load());
    const back = el('a', 'btn btn--text', '返回演出列表');
    back.href = SHOWS_URL;
    actions.appendChild(retry);
    actions.appendChild(back);
    renderStateCard('没有读到演出详情', message || '稍后重试，或先回到演出列表换一场。', actions);
    setPageStatus('读取失败：' + (message || '未知错误'));
}

/* ---------------------------------------------------------------- 首屏 */

function renderPosterFallback(name) {
    const label = nonEmpty(name) ? String(name).trim() : '阿里鱼演出';
    const fallback = el('div', 'poster-fallback');
    fallback.setAttribute('role', 'img');
    fallback.setAttribute('aria-label', '演出海报：' + label);
    fallback.appendChild(el('span', 'poster-fallback__mark', label.slice(0, 1) || '演'));
    return fallback;
}

function renderPoster(detail) {
    detailPoster.classList.remove('is-ready');
    detailPoster.replaceChildren();
    if (!nonEmpty(detail.imgUrl)) {
        detailPoster.replaceChildren(renderPosterFallback(detail.projectName));
        return;
    }
    const image = el('img');
    image.setAttribute('alt', nonEmpty(detail.projectName) ? detail.projectName + ' 海报' : '演出海报');
    image.setAttribute('width', '800');
    image.setAttribute('height', '1000');
    image.setAttribute('decoding', 'async');
    image.setAttribute('referrerpolicy', 'no-referrer');
    image.addEventListener('load', () => detailPoster.classList.add('is-ready'));
    image.addEventListener('error', () => {
        detailPoster.classList.remove('is-ready');
        detailPoster.replaceChildren(renderPosterFallback(detail.projectName));
    });
    image.src = detail.imgUrl;
    detailPoster.appendChild(image);
    if (image.complete && image.naturalWidth > 0) {
        requestAnimationFrame(() => requestAnimationFrame(() => detailPoster.classList.add('is-ready')));
    }
}

function renderHero(detail) {
    heroLoading.hidden = true;
    heroBody.hidden = false;
    detailTitle.textContent = nonEmpty(detail.projectName) ? detail.projectName : '未命名演出';
    const location = [detail.cityName, detail.siteName].filter(nonEmpty).join(' | ');
    detailLoc.textContent = location || '场馆信息待补充';
    detailDate.textContent = formatRange(detail.startTime, detail.endTime) || '演出时间待定';
    detailPrice.textContent = nonEmpty(detail.minPrice) ? '¥' + detail.minPrice + ' 起' : '票价见场次';
    detailPriceDesc.textContent = nonEmpty(detail.lowestPriceDesc) ? detail.lowestPriceDesc : '';
    detailPriceDesc.hidden = !nonEmpty(detail.lowestPriceDesc);

    const status = heroStatus(detail);
    detailSale.classList.toggle('is-on', status.kind === 'on');
    detailSale.classList.toggle('is-error', status.kind === 'error');
    detailSaleText.textContent = status.text;

    renderPoster(detail);
}

/* ---------------------------------------------------------------- 场次 */

function groupByDate(events) {
    const groups = new Map();
    events.forEach(event => {
        const date = ymd(event.showTime);
        if (!date) {
            return;
        }
        if (!groups.has(date)) {
            groups.set(date, []);
        }
        groups.get(date).push(event);
    });
    groups.forEach(list => list.sort((a, b) => Number(a.showTime || 0) - Number(b.showTime || 0)));
    return groups;
}

function renderDateStrip(groups) {
    dateStrip.replaceChildren();
    state.dates.forEach(date => {
        const list = groups.get(date) || [];
        const first = list[0];
        const button = el('button', 'date-btn');
        button.type = 'button';
        button.dataset.date = date;
        button.setAttribute('aria-pressed', String(date === state.date));
        button.setAttribute('aria-label', formatFullDate(first && first.showTime) + '，' + list.length + ' 个场次');
        button.appendChild(el('span', 'date-btn__day', formatMonthDay(first && first.showTime)));
        button.appendChild(el('span', 'date-btn__sub', formatWeekday(first && first.showTime) + ' · ' + list.length + ' 场'));
        button.addEventListener('click', () => selectDate(date));
        dateStrip.appendChild(button);
    });
}

function updateDatePressed() {
    $$('.date-btn', dateStrip).forEach(button => {
        button.setAttribute('aria-pressed', String(button.dataset.date === state.date));
    });
}

function eventRow(event) {
    const row = el('button', 'event-row');
    row.type = 'button';
    row.dataset.eventToken = String(event.eventToken);
    row.setAttribute('aria-pressed', 'false');

    const time = el('span', 'event-row__time');
    const start = formatClock(event.showTime);
    time.appendChild(el('strong', '', start || '时间待定'));
    const end = formatClock(event.showEndTime);
    if (end) {
        time.appendChild(el('span', '', '至 ' + end));
    }

    const body = el('span', 'event-row__body');
    body.appendChild(el('span', 'event-row__caption', nonEmpty(event.eventCaption) ? event.eventCaption : '未命名场次'));
    body.appendChild(el('span', 'event-row__price', priceRange(event)));

    const status = eventStatus(event, state.detail);
    const chip = el('span', 'chip event-row__state ' + status.className, status.text);

    row.appendChild(time);
    row.appendChild(body);
    row.appendChild(chip);
    row.addEventListener('click', () => selectEvent(event));
    return row;
}

function renderEventList(events) {
    eventList.replaceChildren();
    if (!events.length) {
        const empty = el('div', 'empty event-empty');
        empty.appendChild(iconNode('<path d="M12 3v18"/><path d="M3 12h18"/>'));
        const hasOtherDate = state.dates.some(date => date !== state.date);
        empty.appendChild(el('h3', '', state.dates.length ? '这一天没有场次' : '暂无可选场次'));
        empty.appendChild(el('p', '', state.dates.length
            ? '换一个日期看看，或回到演出列表选择其他项目。'
            : '阿里鱼没有返回场次信息，稍后再试。'));
        if (hasOtherDate) {
            const other = state.dates.find(date => date !== state.date);
            const button = el('button', 'btn btn--secondary', '选择其他日期');
            button.type = 'button';
            button.addEventListener('click', () => selectDate(other));
            empty.appendChild(button);
        }
        eventList.appendChild(empty);
        selectEvent(null);
        return;
    }
    events.forEach(event => eventList.appendChild(eventRow(event)));
    const current = state.selectedEvent;
    const keep = current ? events.find(event => String(event.eventToken) === String(current.eventToken)) : null;
    selectEvent(keep || events[0]);
}

function renderSessions() {
    const groups = groupByDate(state.events);
    state.dates = [...groups.keys()].sort();
    if (!state.dates.length) {
        dateStrip.replaceChildren();
        renderEventList([]);
        setSessionsStatus('暂无场次');
        return;
    }
    if (!state.date || !groups.has(state.date)) {
        state.date = state.dates[0];
    }
    renderDateStrip(groups);
    renderEventList(groups.get(state.date) || []);
    setSessionsStatus('共 ' + state.events.length + ' 个场次');
}

async function selectDate(date) {
    if (!date || date === state.date) {
        return;
    }
    state.date = date;
    updateDatePressed();
    const local = state.events.filter(event => ymd(event.showTime) === date);
    const cached = state.byDate.get(date);
    renderEventList(cached || local);
    if (cached) {
        setSessionsStatus('');
        return;
    }
    setSessionsStatus('正在读取 ' + date + ' 的场次…');
    try {
        const data = await api('project/events/by-date', {
            query: { projectToken: state.projectToken, eventDate: date, reqType: 1 }
        });
        if (state.date !== date) {
            return;
        }
        const list = data && Array.isArray(data.eventVoList) ? data.eventVoList : [];
        state.byDate.set(date, list);
        renderEventList(list);
        setSessionsStatus('');
    } catch (e) {
        if (state.date !== date) {
            return;
        }
        state.byDate.set(date, local);
        renderEventList(local);
        setSessionsStatus(e.message);
        toast(e.message, false);
    }
}

/* ---------------------------------------------------------------- 已选场次与提醒 */

function stopCountdown() {
    if (state.countdownTimer) {
        clearInterval(state.countdownTimer);
        state.countdownTimer = 0;
    }
}

function startCountdown(event, node) {
    stopCountdown();
    if (!event || Number(event.countDownState) !== 1) {
        node.hidden = true;
        return;
    }
    const end = parseDate(event.countDownEndTime);
    if (!end) {
        node.hidden = true;
        return;
    }
    const client = Number(event.cTimeMillis);
    const offset = Number.isFinite(client) && client > 0 ? Date.now() - client : 0;
    const remaining = end.getTime() - (Date.now() - offset);
    node.hidden = false;
    if (remaining <= 0) {
        node.textContent = '已开售';
        return;
    }
    node.textContent = '距开售 ' + formatRemaining(remaining);
    state.countdownTimer = setInterval(() => {
        const left = end.getTime() - (Date.now() - offset);
        if (left <= 0) {
            node.textContent = '已开售';
            stopCountdown();
            return;
        }
        node.textContent = '距开售 ' + formatRemaining(left);
    }, 1000);
}

function syncBuyButton(event) {
    const detail = state.detail || {};
    const open = !!event
        && nonEmpty(state.projectToken)
        && Number(detail.saleState) !== 3
        && event.sellOut !== true
        && Number(event.saleState) !== 3
        && Number(event.saleState) !== 1
        && Number(event.countDownState) !== 1
        && Number(event.saleRegisterState) !== 1
        && Number(event.registerEnableState) !== 1;
    buyBtn.hidden = !open;
    remindBtn.hidden = open;
    if (open) {
        buyBtn.href = tplUrl('purchase', {
            projectToken: state.projectToken,
            eventToken: event.eventToken
        });
        buyBtn.textContent = '立即购票';
        remindBtn.className = 'btn btn--secondary summary-cta';
    } else {
        buyBtn.removeAttribute('href');
        remindBtn.className = 'btn btn--primary summary-cta';
    }
}

function syncRemindButton(event) {
    const detail = state.detail || {};
    if (!event) {
        remindBtn.disabled = true;
        remindBtn.textContent = '先选择场次';
        return;
    }
    if (!nonEmpty(state.projectToken)) {
        remindBtn.disabled = true;
        remindBtn.textContent = '暂时无法设置提醒';
        return;
    }
    if (Number(detail.saleState) === 3) {
        remindBtn.disabled = true;
        remindBtn.textContent = '演出已结束';
        return;
    }
    if (event.sellOut === true) {
        remindBtn.disabled = true;
        remindBtn.textContent = '已售罄，无法提醒';
        return;
    }
    if (Number(event.saleState) === 3) {
        remindBtn.disabled = true;
        remindBtn.textContent = '暂不可售，无法提醒';
        return;
    }
    if (Number(event.saleRegisterState) === 1) {
        remindBtn.disabled = true;
        remindBtn.textContent = '已预约开售提醒';
        return;
    }
    if (Number(event.countDownState) !== 1) {
        remindBtn.disabled = true;
        remindBtn.textContent = '该场次暂不支持开售提醒';
        return;
    }
    remindBtn.disabled = false;
    remindBtn.textContent = '预约开售提醒';
}

function renderSummary(event) {
    stopCountdown();
    summaryBody.replaceChildren();
    remindStatus.textContent = '';
    remindStatus.className = 'summary-status';

    if (!event) {
        summaryState.textContent = '还没有选择场次';
        summaryBody.appendChild(el('p', 'summary-empty', '选择左侧的场次，这里会显示时间、票价、限购和开售倒计时。'));
        syncBuyButton(null);
        syncRemindButton(null);
        return;
    }

    summaryState.textContent = eventStatus(event, state.detail).text;

    const selected = el('div', 'summary-selected');
    selected.appendChild(el('p', 'summary-selected__time',
        formatFullDate(event.showTime) + ' ' + (formatClock(event.showTime) || '')));
    if (nonEmpty(event.eventCaption)) {
        selected.appendChild(el('p', 'summary-selected__caption', event.eventCaption));
    }
    summaryBody.appendChild(selected);
    summaryBody.appendChild(el('p', 'summary-price', priceRange(event)));

    const limits = el('div', 'kv');
    if (isCount(event.eventLimitTotal)) {
        limits.appendChild(kvRow('每场限购', event.eventLimitTotal + ' 张'));
    }
    if (isCount(event.projectLimitTotal)) {
        limits.appendChild(kvRow('每项目限购', event.projectLimitTotal + ' 张'));
    }
    if (limits.childElementCount) {
        summaryBody.appendChild(limits);
    }

    const countdown = el('p', 'summary-countdown');
    summaryBody.appendChild(countdown);
    startCountdown(event, countdown);

    if (Number(event.saleRegisterState) === 1) {
        remindStatus.textContent = '该场次已预约开售提醒';
    }
    syncBuyButton(event);
    syncRemindButton(event);
}

/* ---------------------------------------------------------------- 移动端底部操作条 */

let summaryInView = false;
let actionBarKey = '';

/**
 * 把「已选场次」摘要镜像到底部操作条，只在窄屏且摘要卡不在视野内时出现。
 */
function renderActionBar() {
    if (!actionBar) {
        return;
    }
    const event = state.selectedEvent;
    const show = compactQuery.matches
        && !!event
        && !summaryInView
        && !sessionsSection.hidden;

    if (!show) {
        if (actionBarKey !== 'hidden') {
            actionBar.hidden = true;
            actionBarActions.replaceChildren();
            document.body.classList.remove('has-action-bar');
            actionBarKey = 'hidden';
        }
        return;
    }

    const title = formatFullDate(event.showTime) + ' ' + (formatClock(event.showTime) || '');
    const meta = priceRange(event) + ' · ' + eventStatus(event, state.detail).text;
    const buyOpen = !buyBtn.hidden && !!buyBtn.getAttribute('href');
    const actionKey = buyOpen
        ? 'buy|' + buyBtn.textContent.trim() + '|' + buyBtn.getAttribute('href')
        : 'remind|' + remindBtn.textContent.trim() + '|' + remindBtn.disabled;
    const key = title + '|' + meta + '|' + actionKey;
    if (key === actionBarKey) {
        return;
    }
    actionBarKey = key;

    actionBarTitle.textContent = title;
    actionBarMeta.textContent = meta;

    const actions = [];
    if (buyOpen) {
        const link = el('a', 'btn btn--primary', buyBtn.textContent.trim() || '立即购票');
        link.href = buyBtn.getAttribute('href');
        actions.push(link);
    } else {
        const button = el('button', 'btn btn--primary', remindBtn.textContent.trim() || '设置提醒');
        button.type = 'button';
        button.disabled = remindBtn.disabled;
        button.addEventListener('click', () => remindBtn.click());
        actions.push(button);
    }
    actionBarActions.replaceChildren(...actions);
    actionBar.hidden = false;
    document.body.classList.add('has-action-bar');
}

function selectEvent(event) {
    state.selectedEvent = event || null;
    $$('.event-row', eventList).forEach(row => {
        const selected = !!state.selectedEvent && row.dataset.eventToken === String(state.selectedEvent.eventToken);
        row.setAttribute('aria-pressed', String(selected));
    });
    renderSummary(state.selectedEvent);
}

async function remind() {
    const event = state.selectedEvent;
    const detail = state.detail;
    if (!event || !detail || !nonEmpty(state.projectToken) || remindBtn.disabled) {
        return;
    }
    const eventId = Number(event.eventToken);
    if (!Number.isFinite(eventId) || eventId <= 0) {
        remindStatus.textContent = '这个场次的编号不可用，暂时不能设置提醒。';
        remindStatus.classList.add('is-err');
        return;
    }
    setBusy(remindBtn, true, '设置中…');
    remindStatus.textContent = '';
    remindStatus.className = 'summary-status';
    try {
        const accepted = await api('activity-register/update-sale-remind', {
            method: 'POST',
            body: { projectId: state.projectToken, eventId, remindState: 1 }
        });
        if (accepted === false) {
            throw new Error('阿里鱼未受理开售提醒');
        }
        event.saleRegisterState = 1;
        remindStatus.textContent = '已设置开售提醒';
        remindStatus.classList.add('is-ok');
        toast('已设置开售提醒');
        setPageStatus('已设置开售提醒');
    } catch (e) {
        remindStatus.textContent = e.message;
        remindStatus.classList.add('is-err');
        toast(e.message, false);
        setPageStatus('设置开售提醒失败：' + e.message);
    } finally {
        setBusy(remindBtn, false);
        syncRemindButton(state.selectedEvent);
    }
}

/* ---------------------------------------------------------------- 演出介绍 */

function renderIntro(detail) {
    const fragment = sanitizeHtml(detail.projectDetail);
    const hasRich = fragment.childNodes.length > 0;
    if (hasRich) {
        richDetail.replaceChildren(fragment);
    } else {
        richDetail.replaceChildren(el('p', 'hint', '阿里鱼没有提供演出介绍。'));
    }

    introRows.replaceChildren();
    const facts = [
        ['演出时长', detail.showTimeDesc],
        ['入场时间', detail.entryTimeDesc],
        ['儿童票', detail.childTicketDesc]
    ];
    facts.forEach(([label, value]) => {
        if (nonEmpty(value)) {
            introRows.appendChild(kvRow(label, value));
        }
    });
    introFactsCard.hidden = introRows.childElementCount === 0;
    const factsColumn = introFactsCard.parentElement;
    factsColumn.hidden = introFactsCard.hidden;
    const richColumn = richDetail.closest('.span-7');
    if (richColumn) {
        richColumn.classList.toggle('detail-wide', introFactsCard.hidden);
    }

    introSection.hidden = !hasRich && introRows.childElementCount === 0;
}

/* ---------------------------------------------------------------- 场馆与须知 */

function noticeBlock(label, text) {
    const block = el('div', 'notice-block');
    block.appendChild(el('h4', '', label));
    block.appendChild(el('p', '', text));
    return block;
}

function refundTable(details) {
    const table = el('div', 'refund-table');
    details.forEach(detail => {
        if (!detail) {
            return;
        }
        const row = el('div', 'refund-row');
        row.appendChild(el('span', 'refund-row__time', nonEmpty(detail.refundTimeContent) ? detail.refundTimeContent : '—'));
        row.appendChild(el('span', 'refund-row__fee', nonEmpty(detail.refundRuleContent) ? detail.refundRuleContent : '—'));
        table.appendChild(row);
    });
    return table;
}

function renderRefund(rule) {
    if (!rule) {
        return null;
    }
    const type = rule.refundType === null || rule.refundType === undefined ? '' : String(rule.refundType);
    const fee = rule.refundProcedureFeeRule === null || rule.refundProcedureFeeRule === undefined
        ? '' : String(rule.refundProcedureFeeRule);
    const projectDetails = Array.isArray(rule.refundProjectRuleDetails) ? rule.refundProjectRuleDetails : [];
    const eventRules = Array.isArray(rule.refundEventRules) ? rule.refundEventRules : [];
    if (!type && !fee && !projectDetails.length && !eventRules.length) {
        return null;
    }

    const block = el('div', 'notice-block');
    block.appendChild(el('h4', '', '退票规则'));
    if (type === '1') {
        block.appendChild(el('p', '', '该演出不支持退票。'));
        return block;
    }
    if (type === '5') {
        block.appendChild(el('p', '', '该演出支持条件退票，按申请时间收取手续费。'));
    }
    let detailed = false;
    if (fee === '1' && projectDetails.length) {
        block.appendChild(refundTable(projectDetails));
        detailed = true;
    } else if ((fee === '2' || fee === '4') && eventRules.length) {
        eventRules.forEach(eventRule => {
            if (!eventRule) {
                return;
            }
            const wrap = el('div', 'refund-event');
            if (nonEmpty(eventRule.eventName)) {
                wrap.appendChild(el('h4', '', eventRule.eventName));
            }
            const details = Array.isArray(eventRule.refundEventRuleDetails) ? eventRule.refundEventRuleDetails : [];
            if (details.length) {
                wrap.appendChild(refundTable(details));
                detailed = true;
            }
            if (wrap.childElementCount) {
                block.appendChild(wrap);
            }
        });
    } else if (fee === '3') {
        block.appendChild(el('p', '', '退票手续费按阿里鱼页面展示的规则计算。'));
        detailed = true;
    } else if (projectDetails.length) {
        block.appendChild(refundTable(projectDetails));
        detailed = true;
    }
    if (!detailed) {
        block.appendChild(el('p', '', '退票规则以阿里鱼页面为准。'));
    }
    return block;
}

function renderVenue(detail) {
    venueRows.replaceChildren();
    if (nonEmpty(detail.siteName)) {
        venueRows.appendChild(kvRow('场馆', detail.siteName));
    }
    if (nonEmpty(detail.cityName)) {
        venueRows.appendChild(kvRow('城市', detail.cityName));
    }
    venueCard.hidden = venueRows.childElementCount === 0;

    noticeBody.replaceChildren();
    const blocks = [];
    if (nonEmpty(detail.restrictedItem)) {
        blocks.push(noticeBlock('禁止携带物品', detail.restrictedItem));
    }
    if (nonEmpty(detail.depositDesc)) {
        blocks.push(noticeBlock('寄存说明', detail.depositDesc));
    }
    const refund = renderRefund(detail.refundProjectRuleVo);
    if (refund) {
        blocks.push(refund);
    }
    blocks.forEach(block => noticeBody.appendChild(block));
    noticeCard.hidden = blocks.length === 0;

    const venueColumn = venueCard.parentElement;
    const noticeColumn = noticeCard.parentElement;
    venueColumn.hidden = venueCard.hidden;
    noticeColumn.hidden = noticeCard.hidden;
    venueColumn.classList.toggle('detail-wide', noticeCard.hidden && !venueCard.hidden);
    noticeColumn.classList.toggle('detail-wide', venueCard.hidden && !noticeCard.hidden);

    venueSection.hidden = venueCard.hidden && noticeCard.hidden;
}

/* ---------------------------------------------------------------- 热门演出 */

function hotFallback(name) {
    const label = nonEmpty(name) ? String(name).trim() : '阿里鱼';
    const fallback = el('div', 'hot-card__fallback', label.slice(0, 1) || '演');
    fallback.setAttribute('role', 'img');
    fallback.setAttribute('aria-label', '演出海报：' + label);
    return fallback;
}

function renderHotSkeleton() {
    hotGrid.replaceChildren();
    for (let i = 0; i < HOT_LIMIT; i++) {
        const item = el('div', 'hot-skel');
        item.appendChild(el('div', 'skeleton hot-skel__poster'));
        item.appendChild(el('div', 'skeleton skeleton--line'));
        item.appendChild(el('div', 'skeleton skeleton--line'));
        hotGrid.appendChild(item);
    }
}

function renderHot(list) {
    hotGrid.replaceChildren();
    list.forEach(item => {
        const card = el('a', 'hot-card');
        card.href = tplUrl('show-detail', { projectToken: item.projectToken });

        const poster = el('div', 'hot-card__poster');
        if (nonEmpty(item.imgUrl)) {
            const image = el('img');
            image.setAttribute('alt', nonEmpty(item.projectName) ? item.projectName + ' 海报' : '演出海报');
            image.setAttribute('width', '800');
            image.setAttribute('height', '1000');
            image.setAttribute('loading', 'lazy');
            image.setAttribute('decoding', 'async');
            image.setAttribute('referrerpolicy', 'no-referrer');
            image.addEventListener('error', () => {
                if (image.parentNode) {
                    image.replaceWith(hotFallback(item.projectName));
                }
            });
            image.src = item.imgUrl;
            poster.appendChild(image);
        } else {
            poster.appendChild(hotFallback(item.projectName));
        }

        const body = el('div', 'hot-card__body');
        body.appendChild(el('p', 'hot-card__name', nonEmpty(item.projectName) ? item.projectName : '未命名演出'));
        const meta = [item.cityName, item.siteName].filter(nonEmpty).join(' | ');
        if (meta) {
            body.appendChild(el('p', 'hot-card__meta', meta));
        }
        if (nonEmpty(item.minPrice)) {
            body.appendChild(el('p', 'hot-card__price', '¥' + item.minPrice + ' 起'));
        }

        card.appendChild(poster);
        card.appendChild(body);
        hotGrid.appendChild(card);
    });
}

function renderHotError(message) {
    hotGrid.replaceChildren();
    const wrap = el('div', 'hot-error');
    wrap.appendChild(el('p', 'hint', message || '热门演出暂时读不到。'));
    const retry = el('button', 'btn btn--secondary', '重试');
    retry.type = 'button';
    retry.addEventListener('click', () => loadHot());
    wrap.appendChild(retry);
    hotGrid.appendChild(wrap);
}

async function loadHot() {
    const seq = ++state.hotSeq;
    hotSection.hidden = false;
    hotStatus.textContent = '';
    renderHotSkeleton();
    try {
        const data = await api('project/hot', { query: { pageType: 1 } });
        if (seq !== state.hotSeq) {
            return;
        }
        const list = (data && Array.isArray(data.dataList) ? data.dataList : [])
            .filter(item => item && nonEmpty(item.projectToken))
            .slice(0, HOT_LIMIT);
        if (!list.length) {
            hotSection.hidden = true;
            return;
        }
        renderHot(list);
    } catch (e) {
        if (seq !== state.hotSeq) {
            return;
        }
        renderHotError(e.message);
    }
}

/* ---------------------------------------------------------------- 载入 */

async function load() {
    if (!state.projectToken) {
        renderNoToken();
        return;
    }
    const seq = ++state.seq;
    stopCountdown();
    state.detail = null;
    state.events = [];
    state.dates = [];
    state.byDate.clear();
    state.date = '';
    state.selectedEvent = null;

    detailHead.hidden = false;
    stateSection.hidden = true;
    loadingSection.hidden = false;
    DETAIL_SECTIONS.forEach(section => {
        section.hidden = true;
    });
    renderHeroSkeleton();
    setPageStatus('正在读取演出详情');

    try {
        const detail = await api('project/detail', {
            query: { projectToken: state.projectToken, reqType: 1 }
        });
        if (seq !== state.seq) {
            return;
        }
        if (!detail) {
            throw new Error('上游没有返回演出详情');
        }
        state.detail = detail;
        state.events = Array.isArray(detail.eventVoList) ? detail.eventVoList.slice() : [];

        renderHero(detail);
        sessionsSection.hidden = false;
        renderSessions();
        renderIntro(detail);
        renderVenue(detail);
        loadingSection.hidden = true;
        setPageStatus('已读取演出详情');
        loadHot();
    } catch (e) {
        if (seq !== state.seq) {
            return;
        }
        renderError(e.message);
    }
}

remindBtn.addEventListener('click', remind);

if (summaryCard) {
    new MutationObserver(renderActionBar).observe(summaryCard, {
        subtree: true,
        childList: true,
        characterData: true,
        attributes: true,
        attributeFilter: ['hidden', 'disabled', 'href']
    });
    new IntersectionObserver(entries => {
        summaryInView = entries.some(entry => entry.isIntersecting);
        renderActionBar();
    }, { threshold: .2 }).observe(summaryCard);
}
compactQuery.addEventListener('change', renderActionBar);

if (state.projectToken) {
    load();
} else {
    renderNoToken();
}
