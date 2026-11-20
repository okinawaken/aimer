/**
 * 阿里鱼演出购票页：选票档与数量、核价、确认优惠与实名、提交订单，最后给出订单号与支付方式。
 * 上游文本一律用 textContent；协议富文本先经白名单清洗再插入。
 */
import { api, tplUrl } from './aliyu-api.js';
import { $, $$, toast, setBusy } from './aliyu-ui.js';

const QUERY = new URLSearchParams(location.search);
const PROJECT_TOKEN = (QUERY.get('projectToken') || '').trim();
const EVENT_TOKEN = (QUERY.get('eventToken') || '').trim();
const SUPPLIER_CLASS = (QUERY.get('supplierClass') || 'maipay2').trim();
const SHOWS_URL = tplUrl('shows');
const DETAIL_URL = PROJECT_TOKEN ? tplUrl('show-detail', { projectToken: PROJECT_TOKEN }) : SHOWS_URL;
const USER_SETTING_URL = tplUrl('user-setting');
const WEEKDAYS = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
const MAX_TICKETS = 6;

const state = {
    projectToken: PROJECT_TOKEN,
    eventToken: EVENT_TOKEN,
    detail: null,
    event: null,
    events: [],
    dates: [],
    byDate: new Map(),
    date: '',
    tiers: [],
    qty: new Map(),
    coupons: [],
    claimed: new Set(),
    step: 'pick',
    cart: null,
    confirm: null,
    confirmToken: '',
    config: null,
    order: null,
    pay: null,
    bearers: [],
    bearersLoaded: false,
    bearerChoice: new Map(),
    delivery: 1,
    addressId: '',
    orderDiscountId: '',
    promoCode: '',
    sessionPicker: !EVENT_TOKEN,
    countdownTimer: 0,
    seq: 0
};

const purchaseHead = $('#purchaseHead');
const headStatus = $('#headStatus');
const headStatusText = $('#headStatusText');
const headFact = $('#headFact');
const detailLink = $('#detailLink');
const closeDetailLink = $('#closeDetailLink');
const stubNo = $('#stubNo');
const stubShow = $('#stubShow');
const stubDate = $('#stubDate');
const stubVenue = $('#stubVenue');
const stubEvent = $('#stubEvent');
const stubTier = $('#stubTier');
const stubPrice = $('#stubPrice');
const flowRail = $('#flowRail');
const flowLoading = $('#flowLoading');
const flowState = $('#flowState');
const flowStateCard = $('#flowStateCard');
const pickPanel = $('#pickPanel');
const confirmPanel = $('#confirmPanel');
const donePanel = $('#donePanel');
const sessionCard = $('#sessionCard');
const dateStrip = $('#dateStrip');
const eventList = $('#eventList');
const sessionStatus = $('#sessionStatus');
const tierCard = $('#tierCard');
const tierList = $('#tierList');
const tierLimit = $('#tierLimit');
const tierStatus = $('#tierStatus');
const seatCard = $('#seatCard');
const seatMap = $('#seatMap');
const couponCard = $('#couponCard');
const couponList = $('#couponList');
const couponStatus = $('#couponStatus');
const pickState = $('#pickState');
const pickItems = $('#pickItems');
const pickTotal = $('#pickTotal');
const pickLimits = $('#pickLimits');
const pickCta = $('#pickCta');
const pickStatus = $('#pickStatus');
const confirmEvent = $('#confirmEvent');
const goodsList = $('#goodsList');
const deliveryCard = $('#deliveryCard');
const deliveryHint = $('#deliveryHint');
const deliveryModes = $('#deliveryModes');
const deliveryBody = $('#deliveryBody');
const contactRows = $('#contactRows');
const credentialBody = $('#credentialBody');
const discountCard = $('#discountCard');
const discountHint = $('#discountHint');
const discountBody = $('#discountBody');
const promoCodeInput = $('#promoCode');
const applyPromo = $('#applyPromo');
const promoStatus = $('#promoStatus');
const agreementCard = $('#agreementCard');
const agreementBody = $('#agreementBody');
const confirmState = $('#confirmState');
const moneyRows = $('#moneyRows');
const confirmTotal = $('#confirmTotal');
const agreeRow = $('#agreeRow');
const agreeCheck = $('#agreeCheck');
const submitBtn = $('#submitBtn');
const backBtn = $('#backBtn');
const confirmStatus = $('#confirmStatus');
const doneRows = $('#doneRows');
const donePayList = $('#donePayList');
const donePayNote = $('#donePayNote');
const doneLead = $('#doneLead');
const copyOrder = $('#copyOrder');
const againBtn = $('#againBtn');
const doneStatus = $('#doneStatus');
const closeBand = $('#closeBand');
const pickSummary = $('#pickSummary');
const confirmSummary = $('#confirmSummary');
const purchaseBar = $('#purchaseBar');
const purchaseBarTitle = $('#purchaseBarTitle');
const purchaseBarMeta = $('#purchaseBarMeta');
const purchaseBarActions = $('#purchaseBarActions');
const compactQuery = window.matchMedia('(max-width: 1023px)');

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
    return date ? pad2(date.getHours()) + ':' + pad2(date.getMinutes()) : '';
}

function formatMonthDay(value) {
    const date = parseDate(value);
    return date ? (date.getMonth() + 1) + '月' + date.getDate() + '日' : '日期待定';
}

function formatFullDate(value) {
    const date = parseDate(value);
    if (!date) {
        return '时间待定';
    }
    return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日 ' + WEEKDAYS[date.getDay()];
}

function formatEventLine(event) {
    if (!event) {
        return '时间待定';
    }
    const day = formatFullDate(event.showTime);
    const start = formatClock(event.showTime);
    const end = formatClock(event.showEndTime);
    const caption = nonEmpty(event.eventCaption) ? ' · ' + event.eventCaption : '';
    return day + (start ? ' ' + start : '') + (end ? '–' + end : '') + caption;
}

function formatRemaining(millis) {
    const total = Math.max(0, Math.floor(millis / 1000));
    const hours = Math.floor(total / 3600);
    const minutes = Math.floor((total % 3600) / 60);
    const seconds = total % 60;
    return pad2(hours) + ':' + pad2(minutes) + ':' + pad2(seconds);
}

function numberValue(value) {
    if (value === null || value === undefined || value === '') {
        return null;
    }
    const cleaned = String(value).replace(/[^\d.-]/g, '');
    if (!cleaned) {
        return null;
    }
    const number = Number(cleaned);
    return Number.isFinite(number) ? number : null;
}

function money(value) {
    if (value === null || value === undefined || String(value).trim() === '') {
        return '—';
    }
    const text = String(value).trim();
    if (/^[¥￥]/.test(text)) {
        return text;
    }
    const number = numberValue(text);
    if (number === null) {
        return text;
    }
    return '¥' + number.toFixed(2);
}

function rawMoney(value) {
    if (value === null || value === undefined || String(value).trim() === '') {
        return '';
    }
    const text = String(value).trim().replace(/^[¥￥]/, '');
    const number = numberValue(text);
    return number === null ? text : number.toFixed(2);
}

function priceNumber(price) {
    const number = numberValue(price && price.price);
    return number === null ? 0 : number;
}

function limitValue(event, detail) {
    const candidates = [event && event.eventLimitTotal, event && event.projectLimitTotal];
    const numbers = candidates
        .map(value => (value === null || value === undefined || value === '' ? NaN : Number(value)))
        .filter(value => Number.isFinite(value) && value > 0);
    if (!numbers.length) {
        return 0;
    }
    const min = Math.min(...numbers);
    return Number.isFinite(min) && min > 0 ? min : 0;
}

function kvRow(label, value, valueNode) {
    const row = el('div', 'kv-row');
    row.appendChild(el('span', 'k', label));
    if (valueNode) {
        const cell = el('span', 'v');
        cell.appendChild(valueNode);
        row.appendChild(cell);
    } else {
        row.appendChild(el('span', 'v', value));
    }
    return row;
}

function iconNode(paths) {
    const icon = el('div', 'empty__icon');
    icon.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" '
        + 'stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">' + paths + '</svg>';
    return icon;
}

function setStatus(node, message, kind) {
    node.textContent = message || '';
    node.classList.toggle('is-ok', kind === 'ok');
    node.classList.toggle('is-err', kind === 'err');
}

function saleStatus(event, detail) {
    if (!event) {
        return { text: '选择场次', kind: 'quiet', open: false };
    }
    if (Number(detail && detail.saleState) === 3) {
        return { text: '演出已结束', kind: 'quiet', open: false };
    }
    if (event && event.sellOut === true) {
        return { text: '已售罄', kind: 'alert', open: false };
    }
    if (Number(event && event.saleState) === 3) {
        return { text: '暂不可售', kind: 'quiet', open: false };
    }
    if (Number(event && event.saleRegisterState) === 1) {
        return { text: '已预约', kind: 'on', open: false };
    }
    if (Number(event && event.registerEnableState) === 1) {
        return { text: '可预约', kind: 'on', open: false };
    }
    if (Number(event && event.countDownState) === 1) {
        return { text: '即将开售', kind: 'warn', open: false };
    }
    if (Number(event && event.saleState) === 1) {
        return { text: '即将开售', kind: 'warn', open: false };
    }
    return { text: '售票中', kind: 'on', open: true };
}

/* ---------------------------------------------------------------- 富文本白名单 */

const ALLOWED_TAGS = new Set(['P', 'BR', 'STRONG', 'EM', 'B', 'I', 'UL', 'OL', 'LI', 'H3', 'H4', 'A']);
const DROP_TAGS = new Set(['SCRIPT', 'STYLE', 'IFRAME', 'OBJECT', 'SVG', 'EMBED', 'LINK', 'META',
    'TEMPLATE', 'NOSCRIPT', 'CANVAS', 'MATH', 'VIDEO', 'AUDIO', 'SOURCE', 'TRACK', 'BASE', 'TITLE', 'IMG']);

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
            const clean = document.createElement(tag.toLowerCase());
            copyChildren(node, clean);
            target.appendChild(clean);
        });
    };
    copyChildren(parsed.body, output);
    return output;
}

/* ---------------------------------------------------------------- 页面状态 */

function showLoading() {
    flowLoading.hidden = false;
    flowState.hidden = true;
    pickPanel.hidden = true;
    confirmPanel.hidden = true;
    donePanel.hidden = true;
}

function renderStateCard(titleText, message, actions) {
    flowLoading.hidden = true;
    pickPanel.hidden = true;
    confirmPanel.hidden = true;
    donePanel.hidden = true;
    flowState.hidden = false;

    const empty = el('div', 'empty');
    empty.appendChild(iconNode('<path d="M4 5h16v14H4z"/><path d="M4 9h16"/><path d="M8 5v4"/>'));
    const title = el('h2', '', titleText);
    empty.appendChild(title);
    empty.appendChild(el('p', '', message));
    if (actions) {
        empty.appendChild(actions);
    }
    flowStateCard.replaceChildren(empty);
}

function renderNoToken() {
    const actions = el('div', 'btn-row');
    const back = el('a', 'btn btn--primary', '打开演出列表');
    back.href = SHOWS_URL;
    actions.appendChild(back);
    renderStateCard('从演出详情进入购票', '还没有选择演出。先打开一场演出，选好场次，再点「立即购票」。', actions);
}

function renderLoadError(message) {
    const actions = el('div', 'btn-row');
    const retry = el('button', 'btn btn--secondary', '重新加载');
    retry.type = 'button';
    retry.addEventListener('click', () => load());
    const back = el('a', 'btn btn--text', '返回演出列表');
    back.href = SHOWS_URL;
    actions.appendChild(retry);
    actions.appendChild(back);
    renderStateCard('没有读到这场演出', message || '稍后重试，或回到演出列表换一场。', actions);
}

function updateRail() {
    const order = ['pick', 'confirm', 'done'];
    const current = order.indexOf(state.step);
    $$('.flow-rail__step', flowRail).forEach((step, index) => {
        step.classList.toggle('is-current', index === current);
        step.classList.toggle('is-done', index < current);
        if (index === current) {
            step.setAttribute('aria-current', 'step');
        } else {
            step.removeAttribute('aria-current');
        }
    });
    $('#railHintPick').textContent = state.tiers.length
        ? state.tiers.length + ' 个票档' + (totalQty() ? ' · 已选 ' + totalQty() + ' 张' : '')
        : '场次与票档';
    $('#railHintConfirm').textContent = state.confirm
        ? '共 ' + (state.confirm.orderGoodsCount || totalQty()) + ' 件'
        : '优惠与实名';
    $('#railHintDone').textContent = state.order ? '订单 ' + shortOrderNo(state.order.orderId) : '去小程序支付';
}

function showStep(step, moveFocus = true) {
    state.step = step;
    flowLoading.hidden = true;
    flowState.hidden = true;
    pickPanel.hidden = step !== 'pick';
    confirmPanel.hidden = step !== 'confirm';
    donePanel.hidden = step !== 'done';
    updateRail();
    if (!moveFocus) {
        return;
    }
    const heading = step === 'pick'
        ? $('#tier-h')
        : step === 'confirm'
            ? $('#confirm-h')
            : $('#done-h');
    if (heading) {
        heading.setAttribute('tabindex', '-1');
        heading.focus({ preventScroll: true });
    }
    const top = flowRail.getBoundingClientRect().top + window.scrollY - 24;
    window.scrollTo({ top: Math.max(0, top), behavior: 'auto' });
}

function shortOrderNo(value) {
    const text = String(value || '').trim();
    if (!text) {
        return '—';
    }
    return text.length > 10 ? text.slice(0, 6) + '…' + text.slice(-4) : text;
}

/* ---------------------------------------------------------------- 移动端底部操作条 */

let pickSummaryInView = true;
let confirmSummaryInView = true;
let purchaseBarKey = '';
let barFrame = 0;

/**
 * 窄屏时把当前步骤的摘要与主按钮放到屏幕底部；摘要卡本身在视野内就收起，避免重复。
 */
function syncPurchaseBar() {
    if (!purchaseBar) {
        return;
    }
    let kind = '';
    let title = '';
    let meta = '';
    let label = '';
    let disabled = true;
    let inView = true;
    if (!pickPanel.hidden) {
        kind = 'pick';
        title = pickState.textContent.trim();
        meta = '票款合计 ' + pickTotal.textContent.trim();
        label = pickCta.textContent.trim();
        disabled = pickCta.disabled;
        inView = pickSummaryInView;
    } else if (!confirmPanel.hidden) {
        kind = 'confirm';
        title = confirmState.textContent.trim();
        meta = '应收 ' + confirmTotal.textContent.trim();
        label = submitBtn.textContent.trim();
        disabled = submitBtn.disabled;
        inView = confirmSummaryInView;
    }
    const show = compactQuery.matches && !!kind && !inView;
    const key = show ? [kind, title, meta, label, disabled].join('|') : 'hidden';
    if (key === purchaseBarKey) {
        return;
    }
    purchaseBarKey = key;

    if (!show) {
        purchaseBar.hidden = true;
        purchaseBarActions.replaceChildren();
        document.body.classList.remove('has-action-bar');
        return;
    }

    purchaseBarTitle.textContent = title || label || '选择票档';
    purchaseBarMeta.textContent = meta;
    const button = el('button', 'btn btn--primary', label || '继续');
    button.type = 'button';
    button.disabled = disabled;
    button.addEventListener('click', () => {
        if (kind === 'pick') {
            pickCta.click();
        } else {
            submitBtn.click();
        }
    });
    purchaseBarActions.replaceChildren(button);
    purchaseBar.hidden = false;
    document.body.classList.add('has-action-bar');
}

function schedulePurchaseBar() {
    if (barFrame) {
        return;
    }
    barFrame = requestAnimationFrame(() => {
        barFrame = 0;
        syncPurchaseBar();
    });
}

/* ---------------------------------------------------------------- 首屏 */

function renderHero() {
    const detail = state.detail || {};
    const event = state.event;
    const status = saleStatus(event, detail);
    headStatus.classList.toggle('is-on', status.kind === 'on');
    headStatus.classList.toggle('is-error', status.kind === 'error');
    headStatusText.textContent = status.text;

    stubNo.textContent = event ? shortOrderNo(event.eventToken) : '—';
    stubShow.textContent = nonEmpty(detail.projectName) ? detail.projectName : '未命名演出';
    stubDate.textContent = formatEventLine(event);
    stubVenue.textContent = [detail.cityName, detail.siteName].filter(nonEmpty).join(' · ') || '场馆待定';
    stubEvent.textContent = event
        ? (nonEmpty(event.eventCaption) ? event.eventCaption : '未命名场次')
        : '待选择';
    renderStubTier();

    const parts = [];
    if (state.tiers.length) {
        parts.push(state.tiers.length + ' 个票档');
    }
    const limit = limitValue(event, detail);
    if (limit > 0) {
        parts.push('每场限购 ' + limit + ' 张');
    }
    if (nonEmpty(detail.minPrice)) {
        parts.push('票价 ¥' + String(detail.minPrice).replace(/^[¥￥]/, '') + ' 起');
    }
    headFact.textContent = parts.join(' · ');
    headFact.hidden = parts.length === 0;

    detailLink.href = DETAIL_URL;
    closeDetailLink.href = DETAIL_URL;
}

function renderStubTier() {
    const selected = state.tiers.filter(tier => (state.qty.get(String(tier.priceId)) || 0) > 0);
    if (!selected.length) {
        stubTier.textContent = '未选';
        stubPrice.textContent = '—';
        return;
    }
    stubTier.textContent = selected.map(tier => tier.priceName || '票档').join('、');
    const total = selected.reduce((sum, tier) => sum + priceNumber(tier) * (state.qty.get(String(tier.priceId)) || 0), 0);
    stubPrice.textContent = money(total);
}

/* ---------------------------------------------------------------- 场次（无 eventToken 时） */

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
        const firstDate = parseDate(first && first.showTime);
        button.appendChild(el('span', 'date-btn__day', formatMonthDay(first && first.showTime)));
        button.appendChild(el('span', 'date-btn__sub',
            (firstDate ? WEEKDAYS[firstDate.getDay()] : '日期待定') + ' · ' + list.length + ' 场'));
        button.addEventListener('click', () => selectDate(date));
        dateStrip.appendChild(button);
    });
}

function eventRow(event) {
    const row = el('button', 'event-row');
    row.type = 'button';
    row.dataset.eventToken = String(event.eventToken);
    row.setAttribute('aria-pressed', String(String(event.eventToken) === String(state.eventToken)));

    const time = el('span', 'event-row__time');
    time.appendChild(el('strong', '', formatClock(event.showTime) || '待定'));
    const end = formatClock(event.showEndTime);
    if (end) {
        time.appendChild(el('span', '', '至 ' + end));
    }

    const body = el('span', 'event-row__body');
    body.appendChild(el('span', 'event-row__caption', nonEmpty(event.eventCaption) ? event.eventCaption : '未命名场次'));
    const prices = (Array.isArray(event.priceVoList) ? event.priceVoList : [])
        .map(price => priceNumber(price))
        .filter(value => value > 0);
    body.appendChild(el('span', 'event-row__price', prices.length
        ? '¥' + Math.min(...prices).toFixed(2) + ' 起'
        : '价格见票档'));

    const status = saleStatus(event, state.detail);
    const chip = el('span', 'chip event-row__state ' + (status.kind === 'on' ? 'chip--on' : status.kind === 'alert' ? 'chip--alert' : status.kind === 'warn' ? 'chip--warn' : 'chip--quiet'), status.text);

    row.appendChild(time);
    row.appendChild(body);
    row.appendChild(chip);
    row.addEventListener('click', () => chooseEvent(event));
    return row;
}

function renderSessionPicker() {
    const groups = groupByDate(state.events);
    state.dates = [...groups.keys()].sort();
    if (!state.sessionPicker || !state.events.length) {
        // 不带 eventToken 时才需要场次选择器；移出 DOM，避免空的隐藏卡占住左栏顶部
        sessionCard.remove();
        return;
    }
    if (!sessionCard.isConnected) {
        tierCard.parentElement.insertBefore(sessionCard, tierCard);
    }
    sessionCard.hidden = false;
    if (!state.date || !groups.has(state.date)) {
        state.date = state.dates[0];
    }
    renderDateStrip(groups);
    eventList.replaceChildren();
    (groups.get(state.date) || []).forEach(event => eventList.appendChild(eventRow(event)));
    sessionStatus.textContent = '共 ' + state.events.length + ' 个场次';
}

async function selectDate(date) {
    if (!date || date === state.date) {
        return;
    }
    state.date = date;
    $$('.date-btn', dateStrip).forEach(button => {
        button.setAttribute('aria-pressed', String(button.dataset.date === date));
    });
    const cached = state.byDate.get(date);
    if (cached) {
        renderEventRows(cached);
        return;
    }
    sessionStatus.textContent = '正在读取 ' + date + ' 的场次…';
    try {
        const data = await api('project/events/by-date', {
            query: { projectToken: state.projectToken, eventDate: date, reqType: 1 }
        });
        if (state.date !== date) {
            return;
        }
        const list = data && Array.isArray(data.eventVoList) ? data.eventVoList : [];
        state.byDate.set(date, list);
        renderEventRows(list);
        sessionStatus.textContent = '';
    } catch (e) {
        if (state.date !== date) {
            return;
        }
        sessionStatus.textContent = e.message;
        toast(e.message, false);
    }
}

function renderEventRows(list) {
    eventList.replaceChildren();
    if (!list.length) {
        eventList.appendChild(el('p', 'hint', '这一天没有场次，换一个日期看看。'));
        return;
    }
    list.forEach(event => eventList.appendChild(eventRow(event)));
}

async function chooseEvent(event) {
    if (!event || !nonEmpty(event.eventToken)) {
        return;
    }
    state.eventToken = String(event.eventToken);
    state.sessionPicker = false;
    try {
        await loadEvent(state.eventToken);
    } catch (e) {
        state.sessionPicker = true;
        renderSessionPicker();
        setStatus(pickStatus, e.message, 'err');
        toast(e.message, false);
    }
}

/* ---------------------------------------------------------------- 票档与数量 */

function totalQty() {
    let total = 0;
    state.qty.forEach(value => {
        total += Number(value) || 0;
    });
    return total;
}

function tierQty(tier) {
    return state.qty.get(String(tier.priceId)) || 0;
}

function canBuy(event, detail) {
    return saleStatus(event, detail).open;
}

function tierTags(price) {
    const tags = [];
    if (Number(price.earlyBirdTicketTag) === 1) {
        tags.push({ text: '早鸟票', on: true });
    }
    if (Number(price.priceClassify) === 2 && !String(price.priceDiscount || '').includes('学生')) {
        tags.push({ text: '学生票', on: false });
    }
    if (Number(price.couponTicket) === 2 || Number(price.couponTicket) === 3 || Number(price.priceType) === 2) {
        tags.push({ text: '套票', on: false });
    }
    if (nonEmpty(price.priceDiscount)) {
        tags.push({ text: String(price.priceDiscount).trim(), on: false });
    }
    return tags;
}

function stopSaleCountdown() {
    if (state.countdownTimer) {
        clearInterval(state.countdownTimer);
        state.countdownTimer = 0;
    }
}

function renderTiers() {
    tierList.replaceChildren();
    stopSaleCountdown();
    const limit = limitValue(state.event, state.detail);
    const open = canBuy(state.event, state.detail);
    tierLimit.textContent = limit > 0 ? '限购 ' + limit + ' 张' : '不限购';
    tierStatus.textContent = '';
    if (Number(state.event && state.event.countDownState) === 1) {
        startCountdown(state.event, tierStatus);
    } else if (!open) {
        tierStatus.textContent = saleStatus(state.event, state.detail).text;
    }

    if (!state.tiers.length) {
        tierList.appendChild(el('p', 'hint', '阿里鱼没有返回这个场次的票档，换一场或稍后再试。'));
        pickCta.disabled = true;
        return;
    }

    state.tiers.forEach(price => {
        const soldOut = price.sellOut === true || (Number(price.marginCount) === 0);
        const article = el('article', 'tier' + (tierQty(price) > 0 ? ' is-active' : '') + (soldOut ? ' is-soldout' : ''));
        article.dataset.priceId = String(price.priceId);

        const pick = el('button', 'tier__pick');
        pick.type = 'button';
        pick.disabled = soldOut || !open;
        pick.setAttribute('aria-pressed', String(tierQty(price) > 0));
        pick.setAttribute('aria-label', (price.priceName || '票档') + '，' + money(price.price) + (soldOut ? '，已售罄' : ''));

        pick.appendChild(el('span', 'tier__mark'));
        const body = el('span', 'tier__body');
        const name = el('span', 'tier__name');
        const icon = el('span', 'tier__icon');
        icon.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" '
            + 'stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">'
            + '<path d="M4 7h16v4a2 2 0 0 0 0 4v3H4v-3a2 2 0 0 0 0-4z"/><path d="M14 7v10" stroke-dasharray="2 2"/></svg>';
        name.appendChild(icon);
        name.appendChild(document.createTextNode(nonEmpty(price.priceName) ? price.priceName : '票档'));
        body.appendChild(name);
        const tagRow = el('span', 'tier__tags');
        tierTags(price).forEach(tag => tagRow.appendChild(el('span', 'tier__tag' + (tag.on ? ' tier__tag--on' : ''), tag.text)));
        if (soldOut) {
            tagRow.appendChild(el('span', 'tier__tag', '售罄'));
        }
        body.appendChild(tagRow);
        if (nonEmpty(price.couponDesc)) {
            body.appendChild(el('span', 'tier__desc', price.couponDesc));
        } else if (Number(price.marginCount) > 0 && Number(price.priceType) === 2) {
            body.appendChild(el('span', 'tier__desc', '剩余 ' + price.marginCount + ' 套'));
        }
        pick.appendChild(body);

        const priceBox = el('span', 'tier__price');
        priceBox.appendChild(el('strong', '', money(price.price)));
        pick.appendChild(priceBox);

        pick.addEventListener('click', () => {
            if (tierQty(price) > 0) {
                state.qty.delete(String(price.priceId));
            } else {
                setQty(price, 1);
            }
        });

        const stepper = el('div', 'stepper');
        const minus = el('button', 'stepper__btn', '−');
        minus.type = 'button';
        minus.setAttribute('aria-label', '减少 ' + (price.priceName || '票档') + ' 数量');
        minus.disabled = tierQty(price) <= 0;
        const value = el('span', 'stepper__value', String(tierQty(price)));
        value.setAttribute('aria-live', 'polite');
        const plus = el('button', 'stepper__btn', '+');
        plus.type = 'button';
        plus.setAttribute('aria-label', '增加 ' + (price.priceName || '票档') + ' 数量');
        plus.disabled = soldOut || !open || (limit > 0 && totalQty() >= limit)
            || (Number(price.marginCount) > 0 && tierQty(price) >= Number(price.marginCount));

        minus.addEventListener('click', () => setQty(price, Math.max(0, tierQty(price) - 1)));
        plus.addEventListener('click', () => setQty(price, tierQty(price) + 1));
        stepper.appendChild(minus);
        stepper.appendChild(value);
        stepper.appendChild(plus);

        article.appendChild(pick);
        article.appendChild(stepper);
        tierList.appendChild(article);
    });

    if (!open) {
        pickCta.disabled = true;
        pickCta.textContent = saleStatus(state.event, state.detail).text;
    }
}

function setQty(price, count) {
    const limit = limitValue(state.event, state.detail);
    let next = Math.max(0, Math.floor(count || 0));
    if (limit > 0) {
        const others = totalQty() - tierQty(price);
        next = Math.min(next, Math.max(0, limit - others));
    }
    if (Number(price.marginCount) > 0) {
        next = Math.min(next, Number(price.marginCount));
    }
    if (next <= 0) {
        state.qty.delete(String(price.priceId));
    } else {
        state.qty.set(String(price.priceId), next);
    }
    syncTierDom();
    syncSeatDom();
    renderSummary();
    renderStubTier();
    updateRail();
}

/* 只改现有节点，不重建列表：键盘焦点停在加号或减号上 */
function syncTierDom() {
    const limit = limitValue(state.event, state.detail);
    const open = canBuy(state.event, state.detail);
    $$('.tier', tierList).forEach(article => {
        const price = state.tiers.find(item => String(item.priceId) === article.dataset.priceId);
        if (!price) {
            return;
        }
        const count = tierQty(price);
        const soldOut = price.sellOut === true || Number(price.marginCount) === 0;
        article.classList.toggle('is-active', count > 0);
        const pick = $('.tier__pick', article);
        if (pick) {
            pick.setAttribute('aria-pressed', String(count > 0));
        }
        const value = $('.stepper__value', article);
        if (value) {
            value.textContent = String(count);
        }
        const buttons = $$('.stepper__btn', article);
        if (buttons[0]) {
            buttons[0].disabled = count <= 0;
        }
        if (buttons[1]) {
            buttons[1].disabled = soldOut || !open || (limit > 0 && totalQty() >= limit)
                || (Number(price.marginCount) > 0 && count >= Number(price.marginCount));
        }
    });
}

function syncSeatDom() {
    $$('.seat-region', seatMap).forEach(button => {
        const price = state.tiers.find(item => String(item.priceId) === button.dataset.priceId);
        if (!price) {
            return;
        }
        button.setAttribute('aria-pressed', String(tierQty(price) > 0));
    });
}

function renderSeatMap() {
    seatCard.hidden = state.tiers.length < 2;
    seatMap.replaceChildren();
    if (seatCard.hidden) {
        return;
    }
    seatMap.appendChild(el('div', 'seat-stage', '舞 台'));
    const regions = el('div', 'seat-regions');
    state.tiers.forEach(price => {
        const soldOut = price.sellOut === true || Number(price.marginCount) === 0;
        const button = el('button', 'seat-region');
        button.type = 'button';
        button.disabled = soldOut || !canBuy(state.event, state.detail);
        button.dataset.priceId = String(price.priceId);
        button.setAttribute('aria-pressed', String(tierQty(price) > 0));
        button.setAttribute('aria-label', (price.priceName || '票档') + '，' + money(price.price) + (soldOut ? '，已售罄' : ''));
        button.appendChild(el('span', 'seat-region__name', nonEmpty(price.priceName) ? price.priceName : '票档'));
        button.appendChild(el('span', 'seat-region__price', money(price.price)));
        button.addEventListener('click', () => setQty(price, tierQty(price) > 0 ? 0 : 1));
        regions.appendChild(button);
    });
    seatMap.appendChild(regions);
    seatMap.appendChild(el('p', 'seat-note', '区域对应票档；同一票档可以买多张，座位号以出票为准。'));
}

/* ---------------------------------------------------------------- 优惠券 */

function renderCoupons() {
    const list = Array.isArray(state.detail && state.detail.discountInfo) ? state.detail.discountInfo : [];
    state.coupons = list.filter(item => item && nonEmpty(item.discountId));
    couponCard.hidden = state.coupons.length === 0;
    couponList.replaceChildren();
    couponStatus.textContent = '';
    if (!state.coupons.length) {
        return;
    }
    state.coupons.forEach(coupon => {
        const row = el('div', 'coupon-row');
        const body = el('div', 'coupon-row__body');
        body.appendChild(el('p', 'coupon-row__name', nonEmpty(coupon.discountName) ? coupon.discountName : '优惠券'));
        const meta = [];
        if (nonEmpty(coupon.discountStartTimeNew) || nonEmpty(coupon.discountEndTimeNew)) {
            meta.push([coupon.discountStartTimeNew, coupon.discountEndTimeNew].filter(nonEmpty).join(' 至 '));
        }
        if (Number(coupon.discountTimeType) === 2 && Number(coupon.discountFixDay) > 0) {
            meta.push('领取后 ' + coupon.discountFixDay + ' 天有效');
        }
        if (meta.length) {
            body.appendChild(el('p', 'coupon-row__meta', meta.join(' · ')));
        }
        row.appendChild(body);

        const claimed = Number(coupon.receiveStatus) === 1 || state.claimed.has(String(coupon.discountId));
        const button = el('button', 'btn btn--secondary btn--sm', claimed ? '已领取' : '领取');
        button.type = 'button';
        button.disabled = claimed;
        button.addEventListener('click', () => claimCoupon(coupon, button));
        row.appendChild(button);
        couponList.appendChild(row);
    });
}

async function claimCoupon(coupon, button) {
    setBusy(button, true, '领取中…');
    couponStatus.textContent = '';
    try {
        const accepted = await api('order/ticket-discount/receive', {
            query: { ticketId: coupon.discountId }
        });
        if (accepted === false) {
            throw new Error('阿里鱼没有受理这张券');
        }
        coupon.receiveStatus = 1;
        state.claimed.add(String(coupon.discountId));
        button.textContent = '已领取';
        button.disabled = true;
        couponStatus.textContent = '已领取，确认页会自动试算。';
        toast('已领取优惠券');
    } catch (e) {
        couponStatus.textContent = e.message;
        toast(e.message, false);
        setBusy(button, false);
    }
}

/* ---------------------------------------------------------------- 已选汇总 */

function renderSummary() {
    const selected = state.tiers.filter(tier => tierQty(tier) > 0);
    pickItems.replaceChildren();
    if (!selected.length) {
        pickItems.appendChild(el('p', 'pick-empty', '在左侧点「+」加票，或点票档卡选一张。'));
        pickState.textContent = '还没有选票';
        pickTotal.textContent = '¥0.00';
    } else {
        let total = 0;
        selected.forEach(tier => {
            const count = tierQty(tier);
            const amount = priceNumber(tier) * count;
            total += amount;
            const row = el('div', 'pick-item');
            const body = el('div', 'pick-item__name');
            body.appendChild(document.createTextNode(nonEmpty(tier.priceName) ? tier.priceName : '票档'));
            body.appendChild(el('span', 'pick-item__meta', count + ' 张 × ' + money(tier.price)));
            row.appendChild(body);
            row.appendChild(el('span', 'pick-item__amount', money(amount)));
            pickItems.appendChild(row);
        });
        pickState.textContent = '已选 ' + totalQty() + ' 张，' + selected.length + ' 个票档';
        pickTotal.textContent = money(total);
    }

    pickLimits.replaceChildren();
    const limit = limitValue(state.event, state.detail);
    if (limit > 0) {
        pickLimits.appendChild(kvRow('每场限购', limit + ' 张'));
    }
    if (Number(state.event && state.event.projectLimitTotal) > 0) {
        pickLimits.appendChild(kvRow('每项目限购', state.event.projectLimitTotal + ' 张'));
    }
    pickLimits.appendChild(kvRow('已选', totalQty() + ' 张'));

    const open = canBuy(state.event, state.detail);
    const hasTicket = totalQty() > 0;
    pickCta.disabled = !open || !hasTicket;
    pickCta.textContent = open ? '去确认订单' : saleStatus(state.event, state.detail).text;
    pickStatus.textContent = '';
    pickStatus.className = 'summary-status';
}

function startCountdown(event, node) {
    if (state.countdownTimer) {
        clearInterval(state.countdownTimer);
        state.countdownTimer = 0;
    }
    const end = parseDate(event && event.countDownEndTime);
    if (!end) {
        node.textContent = '即将开售，开售时间以阿里鱼为准。';
        return;
    }
    const client = Number(event.cTimeMillis);
    const offset = Number.isFinite(client) && client > 0 ? Date.now() - client : 0;
    const tick = () => {
        const left = end.getTime() - (Date.now() - offset);
        if (left <= 0) {
            node.textContent = '已到开售时间，刷新页面就能买。';
            clearInterval(state.countdownTimer);
            state.countdownTimer = 0;
            return;
        }
        node.textContent = '距开售 ' + formatRemaining(left);
    };
    tick();
    state.countdownTimer = setInterval(tick, 1000);
}

/* ---------------------------------------------------------------- 确认订单 */

function resetConfirmState() {
    state.cart = null;
    state.confirm = null;
    state.confirmToken = '';
    state.config = null;
    state.bearers = [];
    state.bearersLoaded = false;
    state.bearerChoice.clear();
    state.delivery = 1;
    state.addressId = '';
    state.orderDiscountId = '';
}

function renderConfirmLoading() {
    goodsList.replaceChildren();
    for (let i = 0; i < 2; i++) {
        const row = el('div', 'goods-row');
        const body = el('div');
        body.appendChild(el('div', 'skeleton skeleton--line'));
        body.appendChild(el('div', 'skeleton skeleton--line'));
        row.appendChild(body);
        row.appendChild(el('div', 'skeleton', ' '));
        goodsList.appendChild(row);
    }
    moneyRows.replaceChildren();
    moneyRows.appendChild(kvRow('商品总额', '核价中…'));
    confirmTotal.textContent = '—';
    confirmState.textContent = '正在核价';
    deliveryCard.hidden = true;
    contactRows.replaceChildren();
    credentialBody.replaceChildren();
    discountBody.replaceChildren();
    agreementCard.hidden = true;
    agreeCheck.checked = false;
    agreeCheck.disabled = true;
    submitBtn.disabled = true;
}

function selectedCartGoods() {
    return state.tiers
        .filter(tier => tierQty(tier) > 0)
        .map(tier => {
            const count = String(tierQty(tier));
            const combined = Number(tier.couponTicket) === 2
                || Number(tier.couponTicket) === 3
                || Number(tier.priceType) === 2;
            if (combined) {
                return {
                    combinePriceId: String(tier.priceId),
                    childPriceIds: Array.isArray(tier.childPriceIds) ? tier.childPriceIds.map(String) : [],
                    goodCount: count
                };
            }
            return { priceId: String(tier.priceId), goodCount: count };
        });
}

async function goConfirm() {
    if (!canBuy(state.event, state.detail) || totalQty() <= 0) {
        return;
    }
    if (!nonEmpty(state.event && state.event.token)) {
        setStatus(pickStatus, '这个场次没有返回下单凭证，请从详情页重新进入。', 'err');
        return;
    }
    stopSaleCountdown();
    showStep('confirm');
    resetConfirmState();
    await prepareConfirm();
}

async function prepareConfirm() {
    const seq = ++state.seq;
    renderConfirmLoading();
    setStatus(confirmStatus, '', '');
    try {
        const cart = await api('trade/cart/add-good', {
            method: 'POST',
            body: {
                eventId: state.eventToken,
                cartGoods: selectedCartGoods(),
                quickTrade: true,
                pointFlag: false,
                token: state.event.token
            }
        });
        if (seq !== state.seq) {
            return;
        }
        if (!cart) {
            throw new Error('阿里鱼没有返回购物车');
        }
        state.cart = cart;
        const invalid = Array.isArray(cart.invalidGoodVos) ? cart.invalidGoodVos : [];
        if (invalid.length) {
            const names = invalid.map(item => [item.aliasName, item.projectName, item.priceAlias, item.priceName]
                .filter(nonEmpty).join(' ')).filter(nonEmpty);
            throw new Error((names.length ? names.join('；') + '：' : '') + '这些票已经买不到了，返回改票档。');
        }

        const check = await api('trade/shopping-cart/check-good-sale', {
            method: 'POST',
            body: {
                pointFlag: false,
                shopGoods: [],
                quickTrade: true,
                token: cart.token
            }
        });
        if (seq !== state.seq) {
            return;
        }
        const cartToken = (check && check.token) ? check.token : cart.token;
        if (!nonEmpty(cartToken)) {
            throw new Error('没有拿到确认购物车凭证，返回重试。');
        }
        state.confirmToken = cartToken;

        const configPromise = api('trade/render-config', {
            method: 'POST',
            body: {
                goodType: 2,
                countryId: 0,
                supplierClass: SUPPLIER_CLASS,
                langType: '1',
                renderModule: ['cmsTicketClause']
            }
        }).catch(() => null);

        const body = {
            paymentDiscountId: '-1',
            quickTrade: true,
            isOptimumDiscount: true,
            renderConfigParam: {
                phoneDesensitize: true,
                credential: true,
                distribution: true,
                distributionPage: 1,
                distributionPageSize: 20
            },
            token: cartToken,
            isNewUI: true,
            shopGoods: [],
            pointFlag: false,
            ticketExpress: state.delivery
        };
        const previous = state.confirm || {};
        if (state.orderDiscountId) {
            body.orderDiscountId = state.orderDiscountId;
            const discount = (previous.orderDiscounts || [])
                .find(item => String(item.discountId) === String(state.orderDiscountId));
            if (discount) {
                body.orderDiscountRuleId = discount.orderDiscountRuleId;
                body.orderDiscountEventIds = discount.orderDiscountEventIds;
                body.orderDiscountText = discount.discountName;
            }
        }
        if (state.promoCode) {
            body.discountCode = state.promoCode;
            const promo = (previous.promoCodeDiscounts || [])
                .find(item => String(item.discountCode) === String(state.promoCode));
            if (promo) {
                body.discountCodeRuleId = promo.orderDiscountRuleId;
                body.discountCodeEventIds = promo.orderDiscountEventIds;
                body.discountCodeText = promo.discountName;
            }
        }

        const confirm = await api('trade/cart/confirm-grouped', { method: 'POST', body });
        if (seq !== state.seq) {
            return;
        }
        if (!confirm) {
            throw new Error('阿里鱼没有返回确认单');
        }
        if (confirm.emptyShoppingCarts === true) {
            throw new Error('购物车是空的，返回重新选票。');
        }
        state.confirm = confirm;
        state.config = await configPromise;
        if (seq !== state.seq) {
            return;
        }
        renderConfirm();
        await loadBearersIfNeeded();
    } catch (e) {
        if (seq !== state.seq) {
            return;
        }
        setStatus(confirmStatus, e.message, 'err');
        renderConfirmError(e.message);
    }
}

function renderConfirmError(message) {
    goodsList.replaceChildren();
    const box = el('div', 'empty');
    box.appendChild(iconNode('<path d="M12 8v5"/><path d="M12 16h.01"/><circle cx="12" cy="12" r="9"/>'));
    box.appendChild(el('p', '', message || '确认单没有读出来。'));
    const actions = el('div', 'btn-row');
    const retry = el('button', 'btn btn--secondary', '重新核价');
    retry.type = 'button';
    retry.addEventListener('click', () => prepareConfirm());
    const back = el('button', 'btn btn--text', '返回改票档');
    back.type = 'button';
    back.addEventListener('click', () => showStep('pick'));
    actions.appendChild(retry);
    if (state.promoCode) {
        const clear = el('button', 'btn btn--text', '清除优惠码');
        clear.type = 'button';
        clear.addEventListener('click', () => {
            state.promoCode = '';
            promoCodeInput.value = '';
            setStatus(promoStatus, '', '');
            prepareConfirm();
        });
        actions.appendChild(clear);
    }
    actions.appendChild(back);
    box.appendChild(actions);
    goodsList.appendChild(box);
    moneyRows.replaceChildren();
    confirmTotal.textContent = '—';
    confirmState.textContent = '没有核价';
    agreeCheck.disabled = true;
    submitBtn.disabled = true;
}

function renderConfirm() {
    const confirm = state.confirm;
    const detail = state.detail || {};
    const event = state.event;
    confirmEvent.textContent = [detail.projectName, formatEventLine(event)]
        .filter(nonEmpty).join(' · ');

    renderGoods();
    renderDelivery();
    renderContact();
    renderCredentials();
    renderDiscounts();
    renderAgreement();
    renderMoney();

    confirmState.textContent = '共 ' + (confirm.orderGoodsCount || totalQty()) + ' 件，已核价';
    agreeCheck.disabled = false;
    agreeCheck.checked = false;
    submitBtn.disabled = true;
    agreeRow.hidden = false;
    setStatus(confirmStatus, '', '');
}

function renderGoods() {
    const confirm = state.confirm;
    goodsList.replaceChildren();
    const cards = Array.isArray(confirm.cartShowVos) ? confirm.cartShowVos : [];
    const wrap = el('div', 'goods');
    let rendered = false;

    cards.forEach(card => {
        const prices = [
            ...(Array.isArray(card.singleCartPriceShowVos) ? card.singleCartPriceShowVos : []),
            ...(Array.isArray(card.couponCartPriceShowVos) ? card.couponCartPriceShowVos : [])
        ];
        prices.forEach(price => {
            rendered = true;
            const row = el('div', 'goods-row');
            const body = el('div');
            body.appendChild(el('p', 'goods-row__name', nonEmpty(price.priceName)
                ? price.priceName
                : (nonEmpty(card.eventName) ? card.eventName : '演出票')));
            const meta = [card.projectName, card.eventName, card.siteName].filter(nonEmpty).join(' · ');
            if (meta) {
                body.appendChild(el('p', 'goods-row__meta', meta));
            }
            const seats = (Array.isArray(price.goodVos) ? price.goodVos : [])
                .map(seat => [seat.floorName, seat.boundName, seat.rowName, seat.seatName].filter(nonEmpty).join(' '))
                .filter(nonEmpty);
            if (seats.length) {
                body.appendChild(el('p', 'goods-row__seats', seats.join('；')));
            } else if (Number(card.seatType) === 2) {
                body.appendChild(el('p', 'goods-row__meta', '无座 · 自由入场'));
            }
            row.appendChild(body);

            const amount = el('div', 'goods-row__amount');
            amount.appendChild(document.createTextNode(money(price.realMoney || price.price)));
            amount.appendChild(el('small', '', '× ' + (price.goodCount || 1)));
            row.appendChild(amount);
            wrap.appendChild(row);
        });
    });

    if (!rendered) {
        const fallback = Array.isArray(confirm.singleCartShowVos) ? confirm.singleCartShowVos : [];
        fallback.forEach(item => {
            rendered = true;
            const row = el('div', 'goods-row');
            const body = el('div');
            body.appendChild(el('p', 'goods-row__name', nonEmpty(item.modelName) ? item.modelName : (item.projectName || '演出票')));
            const meta = [item.projectName, item.modelType].filter(nonEmpty).join(' · ');
            if (meta) {
                body.appendChild(el('p', 'goods-row__meta', meta));
            }
            row.appendChild(body);
            const amount = el('div', 'goods-row__amount');
            amount.appendChild(document.createTextNode(money(item.sourceMoney)));
            amount.appendChild(el('small', '', '× ' + (item.goodCount || 1)));
            row.appendChild(amount);
            wrap.appendChild(row);
        });
    }

    if (!rendered) {
        const selected = state.tiers.filter(tier => tierQty(tier) > 0);
        selected.forEach(tier => {
            const row = el('div', 'goods-row');
            const body = el('div');
            body.appendChild(el('p', 'goods-row__name', nonEmpty(tier.priceName) ? tier.priceName : '演出票'));
            body.appendChild(el('p', 'goods-row__meta', formatEventLine(state.event)));
            row.appendChild(body);
            const amount = el('div', 'goods-row__amount');
            amount.appendChild(document.createTextNode(money(priceNumber(tier) * tierQty(tier))));
            amount.appendChild(el('small', '', '× ' + tierQty(tier)));
            row.appendChild(amount);
            wrap.appendChild(row);
        });
    }

    goodsList.appendChild(wrap);
}

function deliveryOptions() {
    const confirm = state.confirm || {};
    const codes = [
        ...(Array.isArray(confirm.expressTicket) ? confirm.expressTicket : []),
        ...(Array.isArray(confirm.ticketCerts) ? confirm.ticketCerts : [])
    ].map(Number).filter(Number.isFinite);
    const options = [];
    if (codes.some(code => code === 10 || code === 1) || !codes.length) {
        options.push({ value: 1, label: '电子票 / 自提' });
    }
    if (codes.some(code => code === 2 || code === 9)) {
        options.push({ value: 2, label: '快递纸质票' });
    }
    return options;
}

function renderDelivery() {
    const confirm = state.confirm;
    const options = deliveryOptions();
    deliveryCard.hidden = options.length === 0;
    deliveryModes.replaceChildren();
    deliveryBody.replaceChildren();
    if (!options.length) {
        return;
    }
    if (!options.some(option => option.value === state.delivery)) {
        state.delivery = options[0].value;
    }
    options.forEach(option => {
        const button = el('button', 'choice', option.label);
        button.type = 'button';
        button.setAttribute('aria-pressed', String(state.delivery === option.value));
        button.addEventListener('click', () => {
            if (state.delivery === option.value) {
                return;
            }
            state.delivery = option.value;
            prepareConfirm();
        });
        deliveryModes.appendChild(button);
    });

    const addresses = confirm.userDistributionPager && Array.isArray(confirm.userDistributionPager.dataList)
        ? confirm.userDistributionPager.dataList
        : [];
    if (state.delivery === 2) {
        deliveryHint.textContent = '纸质票寄到收货地址，运费会算进合计。';
        if (!addresses.length) {
            deliveryBody.appendChild(el('p', 'hint', '账号里还没有收货地址，先到「用户设置」添加，再回来核价。'));
            const link = el('a', 'link', '去添加收货地址');
            link.href = USER_SETTING_URL;
            deliveryBody.appendChild(link);
            return;
        }
        if (!addresses.some(address => String(address.disId) === String(state.addressId))) {
            const preferred = addresses.find(address => Number(address.defaultDis) === 1) || addresses[0];
            state.addressId = String(preferred.disId);
        }
        const current = addresses.find(address => String(address.disId) === String(state.addressId)) || addresses[0];
        const box = el('div', 'delivery-address');
        box.appendChild(el('p', 'delivery-address__name', (current.receiverName || '收件人') + ' · ' + (current.telPhone || '电话未填')));
        box.appendChild(el('p', 'delivery-address__line',
            [current.countryName, current.provinceName, current.cityName, current.areaName, current.receiverAddress]
                .filter(nonEmpty).join(' ')));
        deliveryBody.appendChild(box);

        const field = el('div', 'field field--compact');
        const label = el('label', '', '换一个地址');
        label.setAttribute('for', 'addressSelect');
        field.appendChild(label);
        const select = el('select');
        select.id = 'addressSelect';
        addresses.forEach(address => {
            const option = el('option', '', [address.provinceName, address.cityName, address.receiverAddress].filter(nonEmpty).join(' ') || ('地址 ' + address.disId));
            option.value = String(address.disId);
            option.selected = String(address.disId) === String(state.addressId);
            select.appendChild(option);
        });
        select.addEventListener('change', () => {
            state.addressId = select.value;
            prepareConfirm();
        });
        field.appendChild(select);
        deliveryBody.appendChild(field);
    } else {
        deliveryHint.textContent = '下单后凭订单号和小程序里的电子票入场。';
        const eventAddress = Array.isArray(confirm.eventAddress) ? confirm.eventAddress : [];
        const lines = eventAddress
            .filter(item => !Array.isArray(item.locationType) || item.locationType.map(Number).includes(1))
            .map(item => item.address)
            .filter(nonEmpty);
        if (lines.length) {
            const box = el('div', 'delivery-address');
            lines.forEach(line => box.appendChild(el('p', 'delivery-address__line', line)));
            deliveryBody.appendChild(box);
        }
    }
}

function renderContact() {
    const confirm = state.confirm;
    contactRows.replaceChildren();
    const name = nonEmpty(confirm.nameDesensitize) ? confirm.nameDesensitize : '未返回联系人';
    const phone = nonEmpty(confirm.phoneDesensitize) ? confirm.phoneDesensitize : '未返回手机号';
    const area = nonEmpty(confirm.countryAreaCode) ? confirm.countryAreaCode + ' ' : '';
    contactRows.appendChild(kvRow('联系人', name));
    contactRows.appendChild(kvRow('手机号', area + phone));
    if (nonEmpty(confirm.emailDesensitize)) {
        contactRows.appendChild(kvRow('邮箱', confirm.emailDesensitize));
    }
}

function renderCredentials() {
    const confirm = state.confirm;
    credentialBody.replaceChildren();
    if (confirm.closeOneTicketOneCard === true || confirm.closeRealNameLimitBuy === true) {
        credentialBody.appendChild(el('p', 'bearer-note', '本单不需要一票一证，下单后按订单入场。'));
        return;
    }
    const types = (Array.isArray(confirm.credentialList) ? confirm.credentialList : [])
        .map(item => item && item.itemValue)
        .filter(nonEmpty);
    const block = el('div', 'credential-block');
    block.appendChild(el('h4', '', '实名信息'));
    block.appendChild(el('p', 'bearer-note', types.length
        ? '入场证件类型：' + types.join('、') + '。每张票需要一位持票人。'
        : '这场演出一票一证，每张票需要一位持票人。'));
    const list = el('div', 'bearer-list');
    list.id = 'bearerList';
    block.appendChild(list);
    credentialBody.appendChild(block);
    renderBearers();
}

function renderBearers() {
    const list = $('#bearerList');
    if (!list) {
        return;
    }
    list.replaceChildren();
    const count = totalQty();
    if (!state.bearersLoaded) {
        list.appendChild(el('p', 'bearer-note', '正在读取持票人…'));
        return;
    }
    if (!state.bearers.length) {
        list.appendChild(el('p', 'bearer-note', '还没有持票人，先到「用户设置」添加姓名和证件，再回来核价。'));
        const link = el('a', 'link', '去添加持票人');
        link.href = USER_SETTING_URL;
        list.appendChild(link);
        return;
    }
    for (let index = 0; index < count; index += 1) {
        const row = el('div', 'bearer-row');
        row.appendChild(el('span', 'bearer-row__label', '第 ' + (index + 1) + ' 张'));
        const select = el('select');
        select.id = 'bearer-' + index;
        select.setAttribute('aria-label', '第 ' + (index + 1) + ' 张票的持票人');
        const placeholder = el('option', '', '选择持票人');
        placeholder.value = '';
        select.appendChild(placeholder);
        state.bearers.forEach(bearer => {
            const option = el('option', '',
                (bearer.bearerName || '持票人') + (nonEmpty(bearer.identification) ? ' · ' + bearer.identification : ''));
            option.value = String(bearer.bearerId);
            select.appendChild(option);
        });
        const chosen = state.bearerChoice.get(index);
        if (chosen) {
            select.value = String(chosen);
        } else if (state.bearers[index]) {
            select.value = String(state.bearers[index].bearerId);
            state.bearerChoice.set(index, String(state.bearers[index].bearerId));
        }
        select.addEventListener('change', () => {
            if (select.value) {
                state.bearerChoice.set(index, select.value);
            } else {
                state.bearerChoice.delete(index);
            }
        });
        row.appendChild(select);
        list.appendChild(row);
    }
    if (state.bearers.length < count) {
        list.appendChild(el('p', 'bearer-note', '持票人比票少，先补一位持票人再提交。'));
    }
}

async function loadBearersIfNeeded() {
    const confirm = state.confirm;
    if (!confirm || confirm.closeOneTicketOneCard === true || confirm.closeRealNameLimitBuy === true) {
        return;
    }
    try {
        const data = await api('user/bearer/list', {
            query: { page: '1', pageSize: '20', eventIds: state.eventToken }
        });
        state.bearers = (data && Array.isArray(data.dataList) ? data.dataList : [])
            .filter(bearer => bearer && nonEmpty(bearer.bearerId));
    } catch (e) {
        state.bearers = [];
    }
    state.bearersLoaded = true;
    renderBearers();
}

function renderDiscounts() {
    const confirm = state.confirm;
    discountBody.replaceChildren();
    const orderDiscounts = Array.isArray(confirm.orderDiscounts) ? confirm.orderDiscounts : [];
    const promoCodes = Array.isArray(confirm.promoCodeDiscounts) ? confirm.promoCodeDiscounts : [];
    const hasAny = orderDiscounts.length || promoCodes.length;
    discountCard.hidden = false;
    discountHint.textContent = hasAny ? '切换优惠会重新核价，金额以最后一次核价为准。' : '这单没有可叠加的优惠。';

    if (orderDiscounts.length) {
        const list = el('div', 'discount-list');
        const none = discountOption('不使用订单优惠', '', state.orderDiscountId === '');
        none.addEventListener('click', () => {
            if (state.orderDiscountId === '') {
                return;
            }
            state.orderDiscountId = '';
            prepareConfirm();
        });
        list.appendChild(none);
        orderDiscounts.forEach(discount => {
            const option = discountOption(discount.discountName || '订单优惠', money(discount.discountAmount),
                String(state.orderDiscountId) === String(discount.discountId));
            option.addEventListener('click', () => {
                state.orderDiscountId = String(discount.discountId);
                prepareConfirm();
            });
            list.appendChild(option);
        });
        discountBody.appendChild(list);
    }

    if (promoCodes.length) {
        const list = el('div', 'discount-list');
        promoCodes.forEach(discount => {
            const option = discountOption(discount.discountName || discount.discountCode || '优惠码', money(discount.discountAmount), false);
            option.addEventListener('click', () => {
                promoCodeInput.value = discount.discountCode || '';
                state.promoCode = String(discount.discountCode || '');
                prepareConfirm();
            });
            list.appendChild(option);
        });
        discountBody.appendChild(list);
    }
}

function discountOption(name, amount, selected) {
    const label = el('label', 'discount-option');
    const input = el('input');
    input.type = 'radio';
    input.name = 'orderDiscount';
    input.checked = selected;
    label.appendChild(input);
    label.appendChild(el('span', 'discount-option__name', name));
    label.appendChild(el('span', 'discount-option__amount', amount));
    return label;
}

function renderAgreement() {
    const clause = state.config && state.config.cmsTicketClause;
    const html = clause && nonEmpty(clause.ticketClauseContent) ? clause.ticketClauseContent : '';
    agreementCard.hidden = !html;
    agreementBody.replaceChildren();
    if (html) {
        agreementBody.appendChild(sanitizeHtml(html));
    }
}

function moneyRow(label, value) {
    if (!nonEmpty(value)) {
        return null;
    }
    const number = numberValue(value);
    if (number !== null && number === 0) {
        return null;
    }
    return kvRow(label, money(value));
}

function renderMoney() {
    const confirm = state.confirm;
    moneyRows.replaceChildren();
    const rows = [
        moneyRow('商品总额', confirm.orderGoodsOriginalMoney || confirm.totalMoneyAmount || confirm.orderOriginMoney),
        moneyRow('票品优惠', confirm.priceDiscountMoney),
        moneyRow('订单优惠', confirm.orderDiscountMoney),
        moneyRow('优惠码', confirm.promoCodeDiscountMoney),
        moneyRow('支付优惠', confirm.paymentDiscountMoney),
        moneyRow('积分抵扣', confirm.useDeductionMoney),
        moneyRow('余额抵扣', confirm.useBalanceMoney),
        moneyRow('礼品卡', confirm.useGiftCardMoney),
        moneyRow('运费', confirm.distributionMoney)
    ].filter(Boolean);
    rows.forEach(row => moneyRows.appendChild(row));
    if (!rows.length) {
        moneyRows.appendChild(kvRow('商品总额', money(confirm.totalMoneyAmount || confirm.orderTotalMoney)));
    }
    const due = confirm.receivableMoney || confirm.onlinePaymentMoney || confirm.orderTotalMoney || confirm.totalMoneyAmount;
    confirmTotal.textContent = money(due);
}

/* ---------------------------------------------------------------- 提交订单 */

function bearerSelectionComplete() {
    if (state.confirm && (state.confirm.closeOneTicketOneCard === true || state.confirm.closeRealNameLimitBuy === true)) {
        return true;
    }
    const count = totalQty();
    for (let index = 0; index < count; index += 1) {
        if (!state.bearerChoice.get(index)) {
            return false;
        }
    }
    return true;
}

function buildSubmitBody() {
    const confirm = state.confirm;
    const body = {
        quickTrade: true,
        useDeductionPoint: 0,
        useDeductionMoney: '0.00',
        paymentDiscountId: confirm.paymentDiscountId || '-1',
        receivableMoney: rawMoney(confirm.receivableMoney || confirm.onlinePaymentMoney || confirm.orderTotalMoney),
        sessionId: confirm.sessionId,
        checkGoods: [{ eventId: state.eventToken }],
        shopGoods: [],
        ticketExpress: state.delivery,
        pointFlag: false,
        token: confirm.token || state.confirmToken
    };
    if (state.orderDiscountId) {
        body.orderDiscountId = state.orderDiscountId;
        const discount = (confirm.orderDiscounts || []).find(item => String(item.discountId) === String(state.orderDiscountId));
        if (discount) {
            body.orderDiscountRuleId = discount.orderDiscountRuleId;
        }
    }
    if (state.promoCode) {
        body.discountCode = state.promoCode;
        const promo = (confirm.promoCodeDiscounts || []).find(item => String(item.discountCode) === String(state.promoCode));
        if (promo) {
            body.discountCodeRuleId = promo.orderDiscountRuleId;
        }
    }

    if (state.delivery === 2) {
        const addresses = confirm.userDistributionPager && Array.isArray(confirm.userDistributionPager.dataList)
            ? confirm.userDistributionPager.dataList
            : [];
        const address = addresses.find(item => String(item.disId) === String(state.addressId));
        if (address) {
            body.orderReceiveParam = {
                distributionMoney: rawMoney(confirm.distributionMoney),
                disId: String(address.disId),
                expressPayType: 1,
                receiveType: 9,
                ticketType: 9,
                receiveName: address.receiverName || '',
                cardType: '',
                receiveCard: ''
            };
        }
    }

    const bearerIds = [];
    for (let index = 0; index < totalQty(); index += 1) {
        const bearerId = state.bearerChoice.get(index);
        if (bearerId) {
            bearerIds.push(bearerId);
        }
    }
    if (bearerIds.length) {
        if (Number(confirm.limitTotalType) === 1) {
            body.orderRestrict = { bearerId: bearerIds[0] };
        } else {
            const tickets = [];
            state.tiers.forEach(tier => {
                const count = tierQty(tier);
                for (let index = 0; index < count; index += 1) {
                    tickets.push({ priceId: String(tier.priceId) });
                }
            });
            body.ticketRestricts = tickets.map((ticket, index) => ({
                priceId: ticket.priceId,
                bearerId: bearerIds[index] || bearerIds[bearerIds.length - 1]
            }));
        }
    }
    return body;
}

async function submitOrder() {
    if (!state.confirm || submitBtn.disabled) {
        return;
    }
    if (!agreeCheck.checked) {
        setStatus(confirmStatus, '先勾选购票协议。', 'err');
        return;
    }
    if (state.delivery === 2) {
        const addresses = state.confirm.userDistributionPager && Array.isArray(state.confirm.userDistributionPager.dataList)
            ? state.confirm.userDistributionPager.dataList
            : [];
        if (!addresses.length || !state.addressId) {
            setStatus(confirmStatus, '快递纸质票需要一个收货地址，先添加或选择地址。', 'err');
            return;
        }
    }
    if (!bearerSelectionComplete()) {
        setStatus(confirmStatus, '每张票都要选一位持票人。', 'err');
        return;
    }
    setBusy(submitBtn, true, '提交中…');
    setStatus(confirmStatus, '正在提交订单…', '');
    try {
        const order = await api('trade/cart/submit-grouped', {
            method: 'POST',
            body: buildSubmitBody()
        });
        if (!order || !nonEmpty(order.orderId)) {
            throw new Error('阿里鱼没有返回订单号');
        }
        state.order = { orderId: String(order.orderId) };
        setStatus(confirmStatus, '订单已生成', 'ok');
        await loadPay();
        showStep('done');
        renderDone();
        toast('订单已提交');
    } catch (e) {
        setStatus(confirmStatus, e.message, 'err');
        toast(e.message, false);
    } finally {
        setBusy(submitBtn, false);
        submitBtn.disabled = !agreeCheck.checked;
    }
}

async function loadPay() {
    state.pay = null;
    if (!state.order) {
        return;
    }
    try {
        const pay = await api('checkout/render-pay', {
            query: {
                supplierClass: SUPPLIER_CLASS,
                outerPaymentNo: state.order.orderId
            }
        });
        state.pay = pay || null;
    } catch (e) {
        state.pay = null;
    }
}

/* ---------------------------------------------------------------- 完成页 */

function stopOrderCountdown() {
    if (state.countdownTimer) {
        clearInterval(state.countdownTimer);
        state.countdownTimer = 0;
    }
}

function renderDone() {
    stopOrderCountdown();
    const confirm = state.confirm || {};
    const detail = state.detail || {};
    const order = state.order || {};
    doneRows.replaceChildren();
    doneRows.appendChild(kvRow('订单号', order.orderId || '—'));
    doneRows.appendChild(kvRow('演出', detail.projectName || '演出'));
    doneRows.appendChild(kvRow('场次', formatEventLine(state.event)));
    const selected = state.tiers.filter(tier => tierQty(tier) > 0);
    if (selected.length) {
        doneRows.appendChild(kvRow('票档', selected.map(tier => (tier.priceName || '票档') + ' × ' + tierQty(tier)).join('，')));
    }
    const amount = state.pay && nonEmpty(state.pay.orderMoney)
        ? state.pay.orderMoney
        : (confirm.receivableMoney || confirm.onlinePaymentMoney || confirm.orderTotalMoney);
    doneRows.appendChild(kvRow('应付金额', money(amount)));

    const countdownRow = kvRow('支付剩余', '—');
    countdownRow.dataset.countdown = '1';
    doneRows.appendChild(countdownRow);

    donePayList.replaceChildren();
    const pays = state.pay && Array.isArray(state.pay.payTypeVOList) ? state.pay.payTypeVOList : [];
    if (pays.length) {
        pays.forEach(pay => {
            const item = el('li');
            if (nonEmpty(pay.icon)) {
                const image = el('img');
                image.src = pay.icon;
                image.alt = '';
                image.width = 48;
                image.height = 48;
                image.loading = 'lazy';
                image.referrerPolicy = 'no-referrer';
                item.appendChild(image);
            }
            item.appendChild(el('span', 'pay-list__name', nonEmpty(pay.gateWayName) ? pay.gateWayName : '支付方式'));
            if (nonEmpty(pay.paySupplierCode)) {
                item.appendChild(el('span', 'pay-list__code', pay.paySupplierCode));
            }
            donePayList.appendChild(item);
        });
        donePayNote.textContent = '这些支付方式来自阿里鱼收银台；在小程序里选择其中一个完成付款。';
    } else {
        donePayNote.textContent = '收银台没有返回支付方式，打开阿里鱼小程序后在「我的订单」里支付。';
    }

    const failure = state.pay ? parseDate(state.pay.orderFailureTime) : null;
    if (failure) {
        const system = Number(state.pay.systemTime);
        const offset = Number.isFinite(system) && system > 0 ? Date.now() - system : 0;
        const tick = () => {
            const left = failure.getTime() - (Date.now() - offset);
            const cell = doneRows.querySelector('[data-countdown] .v');
            if (!cell) {
                return;
            }
            if (left <= 0) {
                cell.textContent = '已超时';
                stopOrderCountdown();
                setStatus(doneStatus, '支付时间已过，订单可能已经关闭。回小程序确认，或重新下单。', 'err');
                return;
            }
            cell.textContent = formatRemaining(left);
        };
        tick();
        state.countdownTimer = setInterval(tick, 1000);
        doneLead.textContent = '订单已经生成。打开阿里鱼小程序的「我的订单」，用订单号 ' + order.orderId + ' 完成支付。';
    } else {
        const cell = doneRows.querySelector('[data-countdown] .v');
        if (cell) {
            cell.textContent = '以小程序为准';
        }
        doneLead.textContent = '订单已经生成。打开阿里鱼小程序的「我的订单」，用订单号 ' + order.orderId + ' 完成支付。';
    }

    doneStatus.textContent = '';
    doneStatus.className = 'summary-status';
}

async function copyOrderNo() {
    const text = state.order ? String(state.order.orderId) : '';
    if (!text) {
        return;
    }
    try {
        await navigator.clipboard.writeText(text);
        setStatus(doneStatus, '订单号已复制', 'ok');
    } catch (e) {
        setStatus(doneStatus, '复制失败，手动选中订单号：' + text, 'err');
    }
}

function buyAgain() {
    stopOrderCountdown();
    resetConfirmState();
    state.order = null;
    state.pay = null;
    state.qty.clear();
    agreeCheck.checked = false;
    submitBtn.disabled = true;
    tierCard.hidden = false;
    renderTiers();
    renderSeatMap();
    renderSummary();
    renderStubTier();
    updateRail();
    showStep('pick');
}

/* ---------------------------------------------------------------- 载入 */

async function loadEvent(eventToken) {
    const event = await api('project/event', { query: { eventToken } });
    if (!event) {
        throw new Error('阿里鱼没有返回这个场次');
    }
    state.eventToken = String(eventToken);
    state.event = event;
    state.tiers = (Array.isArray(event.priceVoList) ? event.priceVoList : [])
        .filter(price => price && nonEmpty(price.priceId));
    state.qty.clear();
    state.claimed.clear();
    state.coupons = [];
    state.orderDiscountId = '';
    state.promoCode = '';
    promoCodeInput.value = '';
    setStatus(promoStatus, '', '');
    tierCard.hidden = false;
    renderHero();
    renderSessionPicker();
    renderTiers();
    renderSeatMap();
    renderCoupons();
    renderSummary();
    updateRail();
    showStep('pick', false);
}

async function load() {
    if (!state.projectToken) {
        renderNoToken();
        return;
    }
    showLoading();
    try {
        const detail = await api('project/detail', {
            query: { projectToken: state.projectToken, reqType: 1 }
        });
        if (!detail) {
            throw new Error('上游没有返回演出详情');
        }
        state.detail = detail;
        state.events = Array.isArray(detail.eventVoList) ? detail.eventVoList.slice() : [];
        closeBand.hidden = false;

        if (state.eventToken) {
            await loadEvent(state.eventToken);
        } else if (state.events.length) {
            renderHero();
            state.sessionPicker = true;
            renderSessionPicker();
            tierCard.hidden = true;
            seatCard.hidden = true;
            couponCard.hidden = true;
            pickItems.replaceChildren(el('p', 'pick-empty', '先在上面选一场，再挑票档。'));
            pickState.textContent = '还没有选择场次';
            pickCta.disabled = true;
            pickCta.textContent = '先选择场次';
            updateRail();
            showStep('pick', false);
        } else {
            throw new Error('这场演出没有可售场次');
        }
    } catch (e) {
        renderLoadError(e.message);
    }
}

pickCta.addEventListener('click', goConfirm);
backBtn.addEventListener('click', () => {
    showStep('pick');
    renderSummary();
});
agreeCheck.addEventListener('change', () => {
    submitBtn.disabled = !agreeCheck.checked || !state.confirm;
    setStatus(confirmStatus, '', '');
});
submitBtn.addEventListener('click', submitOrder);
copyOrder.addEventListener('click', copyOrderNo);
againBtn.addEventListener('click', buyAgain);
applyPromo.addEventListener('click', () => {
    const code = promoCodeInput.value.trim();
    if (!code) {
        setStatus(promoStatus, '先输入优惠码。', 'err');
        return;
    }
    state.promoCode = code;
    setStatus(promoStatus, '正在核价…', '');
    prepareConfirm();
});
promoCodeInput.addEventListener('keydown', event => {
    if (event.key === 'Enter') {
        event.preventDefault();
        applyPromo.click();
    }
});

if (pickSummary) {
    new IntersectionObserver(entries => {
        pickSummaryInView = entries.some(entry => entry.isIntersecting);
        schedulePurchaseBar();
    }, { threshold: .2 }).observe(pickSummary);
}
if (confirmSummary) {
    new IntersectionObserver(entries => {
        confirmSummaryInView = entries.some(entry => entry.isIntersecting);
        schedulePurchaseBar();
    }, { threshold: .2 }).observe(confirmSummary);
}
const barObserver = new MutationObserver(schedulePurchaseBar);
[pickPanel, confirmPanel].forEach(panel => {
    barObserver.observe(panel, {
        subtree: true,
        childList: true,
        characterData: true,
        attributes: true,
        attributeFilter: ['disabled', 'hidden', 'href']
    });
});
compactQuery.addEventListener('change', syncPurchaseBar);

if (state.projectToken) {
    load();
} else {
    renderNoToken();
}
