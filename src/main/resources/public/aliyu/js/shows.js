import { api, tplUrl } from './aliyu-api.js';
import { $, $$ } from './aliyu-ui.js';

const PAGE_SIZE = 12;
const CATEGORY_LIMIT = 10;
const FILTER_ERROR_NOTE = '部分筛选条件没有读到，仍可按其余条件筛选。';

const state = {
    projectClass: '',
    city: '',
    startTime: '',
    endTime: '',
    projectName: '',
    page: 1,
    pageSize: PAGE_SIZE,
    totalPage: 0,
    totalRow: 0,
    types: [],
    cities: [],
    hotList: [],
    hotItem: null,
    listFirstItem: null,
    heroKey: '',
    hotSettled: false,
    listSettled: false,
    filterErrors: new Set(),
    listSeq: 0,
    listController: null,
    reduced: false
};

const heroShow = $('#heroShow');
const filterForm = $('#filterForm');
const catChips = $('#catChips');
const citySelect = $('#city');
const startInput = $('#startDate');
const endInput = $('#endDate');
const searchInput = $('#search');
const filterSummary = $('#filterSummary');
const filterNote = $('#filterNote');
const btnClear = $('#btnClear');
const listSection = $('#list');
const listHeading = $('#list-h');
const listStatus = $('#listStatus');
const grid = $('#showGrid');
const emptyState = $('#emptyState');
const errorState = $('#errorState');
const errorMessage = $('#errorMessage');
const btnEmptyClear = $('#btnEmptyClear');
const btnRetry = $('#btnRetry');
const pager = $('#pager');
const prevPage = $('#prevPage');
const nextPage = $('#nextPage');
const pageNums = $('#pageNums');
const pageMeta = $('#pageMeta');

const reducedQuery = window.matchMedia('(prefers-reduced-motion: reduce)');
state.reduced = reducedQuery.matches;

function firstChar(text) {
    return String(text || '').trim().slice(0, 1) || '演';
}

function el(tag, className, text) {
    const node = document.createElement(tag);
    if (className) {
        node.className = className;
    }
    if (text !== undefined && text !== null) {
        node.textContent = String(text);
    }
    return node;
}

function isAbort(error) {
    return Boolean(error && error.name === 'AbortError');
}

function pad(value) {
    return String(value).padStart(2, '0');
}

function formatDate(value) {
    if (value === null || value === undefined || value === '') {
        return '';
    }
    const date = new Date(Number(value));
    if (Number.isNaN(date.getTime())) {
        return '';
    }
    return date.getFullYear() + '-' + pad(date.getMonth() + 1) + '-' + pad(date.getDate());
}

function formatDateTime(value) {
    if (value === null || value === undefined || value === '') {
        return '';
    }
    const date = new Date(Number(value));
    if (Number.isNaN(date.getTime())) {
        return '';
    }
    return pad(date.getMonth() + 1) + '-' + pad(date.getDate())
        + ' ' + pad(date.getHours()) + ':' + pad(date.getMinutes());
}

function formatRange(start, end) {
    const from = formatDate(start);
    const to = formatDate(end);
    if (from && to) {
        return from === to ? from : from + ' 至 ' + to;
    }
    return from || to;
}

function priceAmount(value) {
    const text = String(value === null || value === undefined ? '' : value).trim();
    if (!text) {
        return '';
    }
    return text.startsWith('¥') ? text : '¥' + text;
}

function detailUrl(token) {
    return tplUrl('show-detail', { projectToken: token });
}

function toInputDate(value) {
    if (!value) {
        return '';
    }
    const match = String(value).match(/^(\d{4})[/-](\d{1,2})[/-](\d{1,2})$/);
    if (!match) {
        return '';
    }
    return match[1] + '-' + pad(match[2]) + '-' + pad(match[3]);
}

function toQueryDate(value) {
    return value ? value.replace(/-/g, '/') : '';
}

function statusOf(item) {
    if (Number(item.saleState) === 3) {
        return { kind: 'ended', text: '已结束' };
    }
    if (item.sellOut === true) {
        return { kind: 'soldout', text: '售罄' };
    }
    if (Number(item.countDownState) === 1) {
        const days = Number(item.countDownShowDay);
        const text = Number.isFinite(days) && days >= 1 ? days + ' 天后开售' : '即将开售';
        const when = formatDateTime(item.countDownEndTimeMillis);
        return { kind: 'soon', text, note: when ? '开售时间 ' + when : '' };
    }
    return { kind: 'onsale', text: '在售' };
}

function statusChip(item) {
    const status = statusOf(item);
    const chip = el('span', 'show-status show-status--' + status.kind, status.text);
    if (status.note) {
        chip.title = status.note;
        chip.appendChild(el('span', 'visually-hidden', '，' + status.note));
    }
    return chip;
}

function posterNode(item, className, eager = false) {
    const poster = el('div', className);
    const src = String(item.imgUrl || '').trim();
    const fallback = el('div', 'poster-fallback');
    fallback.hidden = true;
    fallback.setAttribute('role', 'img');
    fallback.setAttribute('aria-label', '演出海报：' + (item.projectName || '演出'));
    fallback.appendChild(el('span', 'poster-fallback__mark', firstChar(item.projectName)));

    if (src) {
        const img = document.createElement('img');
        img.alt = '';
        img.width = 800;
        img.height = 1000;
        img.setAttribute('loading', eager ? 'eager' : 'lazy');
        img.setAttribute('decoding', 'async');
        img.setAttribute('referrerpolicy', 'no-referrer');
        img.addEventListener('error', () => {
            img.hidden = true;
            fallback.hidden = false;
        }, { once: true });
        img.src = src;
        poster.appendChild(img);
    } else {
        fallback.hidden = false;
    }
    poster.appendChild(fallback);
    return poster;
}

function cleanText(value) {
    return value === null || value === undefined ? '' : String(value).trim();
}

function joinMeta(values) {
    return values.map(cleanText).filter(Boolean).join(' · ');
}

function showCard(item) {
    const token = String(item.projectToken || '').trim();
    const card = token
        ? el('a', 'card show-card m-zoom')
        : el('article', 'card show-card m-nolift');
    if (token) {
        card.href = detailUrl(token);
    }
    if (Number(item.saleState) === 3) {
        card.classList.add('is-ended');
    }
    if (item.sellOut === true) {
        card.classList.add('is-soldout');
    }

    const poster = posterNode(item, 'show-card__poster');
    poster.appendChild(statusChip(item));
    card.appendChild(poster);

    const body = el('div', 'show-card__body');
    const place = joinMeta([item.cityName, item.siteName]);
    body.appendChild(el('p', 'show-card__meta', place || '城市与场馆见详情'));
    body.appendChild(el('h3', 'show-card__name', item.projectName || '未命名演出'));

    const time = formatRange(item.startTime, item.endTime);
    body.appendChild(el('p', 'show-card__time', time || '时间见详情'));

    const tags = joinMeta([item.projectTypeName, item.showTimeDesc]);
    if (tags) {
        body.appendChild(el('p', 'show-card__tags', tags));
    }

    const foot = el('div', 'show-card__foot');
    const price = el('p', 'show-card__price');
    const amount = priceAmount(item.minPrice);
    if (amount) {
        price.appendChild(el('span', 'show-card__amount', amount));
        price.appendChild(document.createTextNode(' 起'));
    } else {
        price.textContent = '价格见详情';
    }
    foot.appendChild(price);
    if (item.lowestPriceDesc) {
        foot.appendChild(el('span', 'show-card__tag', item.lowestPriceDesc));
    }
    body.appendChild(foot);
    card.appendChild(body);
    return card;
}

function renderHero(item) {
    heroShow.replaceChildren();
    if (!item) {
        const card = el('div', 'card hero-show hero-show--type m-nolift');
        card.appendChild(el('span', 'hero-show__label', '阿里鱼'));
        const body = el('div', 'hero-show__type-body');
        body.appendChild(el('p', 'hero-show__type-title', '演出列表'));
        body.appendChild(el('p', 'hero-show__type-line', '按分类、城市和日期筛选。'));
        body.appendChild(el('p', 'hero-show__type-line', '价格直接标在卡片上。'));
        card.appendChild(body);
        heroShow.appendChild(card);
        return;
    }

    const token = String(item.projectToken || '').trim();
    const card = token
        ? el('a', 'card hero-show m-zoom')
        : el('div', 'card hero-show m-nolift');
    if (token) {
        card.href = detailUrl(token);
    }

    const poster = posterNode(item, 'hero-show__poster', true);
    poster.appendChild(statusChip(item));
    card.appendChild(poster);

    const body = el('div', 'hero-show__body');
    body.appendChild(el('span', 'hero-show__label', state.hotItem ? '热门' : '演出'));
    body.appendChild(el('p', 'hero-show__name', item.projectName || '未命名演出'));

    const place = joinMeta([item.cityName, item.siteName]);
    if (place) {
        body.appendChild(el('p', 'hero-show__meta', place));
    }
    const time = formatRange(item.startTime, item.endTime);
    if (time) {
        body.appendChild(el('p', 'hero-show__time', time));
    }

    const price = el('p', 'hero-show__price');
    const amount = priceAmount(item.minPrice);
    if (amount) {
        price.appendChild(el('span', 'hero-show__amount', amount));
        price.appendChild(document.createTextNode(' 起'));
    } else {
        price.textContent = '价格见详情';
    }
    body.appendChild(price);
    card.appendChild(body);
    heroShow.appendChild(card);
}

function updateHeroFact(item) {
    const node = $('#heroFact');
    if (!node) {
        return;
    }
    if (item && item.minPrice) {
        const place = item.cityName ? item.cityName + ' · ' : '';
        node.textContent = place + '价格从 ¥' + item.minPrice + ' 起';
        node.hidden = false;
        return;
    }
    if (state.totalRow > 0) {
        node.textContent = '本周 ' + state.totalRow + ' 个演出在售';
        node.hidden = false;
        return;
    }
    node.hidden = true;
}

function maybeRenderHero() {
    if (!state.hotSettled) {
        return;
    }
    const item = state.hotItem || state.listFirstItem || null;
    if (!item && !state.listSettled) {
        return;
    }
    updateHeroFact(item);
    const key = item
        ? [state.hotItem ? 'hot' : 'list', item.projectToken || '', item.projectName || ''].join('|')
        : 'empty';
    if (state.heroKey === key) {
        return;
    }
    state.heroKey = key;
    renderHero(item);
}

function flattenTypes(nodes, out = [], seen = new Set()) {
    (Array.isArray(nodes) ? nodes : []).forEach(node => {
        if (!node) {
            return;
        }
        const id = node.projectTypeId === null || node.projectTypeId === undefined
            ? ''
            : String(node.projectTypeId);
        const name = node.projectTypeName ? String(node.projectTypeName) : '';
        if (id && name && !seen.has(id)) {
            seen.add(id);
            out.push({ id, name });
        }
        if (Array.isArray(node.children) && node.children.length) {
            flattenTypes(node.children, out, seen);
        }
    });
    return out;
}

function flattenCities(nodes, out = [], seen = new Set()) {
    (Array.isArray(nodes) ? nodes : []).forEach(node => {
        if (!node) {
            return;
        }
        const children = Array.isArray(node.cityList) ? node.cityList : [];
        if (children.length) {
            flattenCities(children, out, seen);
            return;
        }
        const id = node.areaId === null || node.areaId === undefined ? '' : String(node.areaId);
        const name = node.areaName ? String(node.areaName) : '';
        if (id && name && !seen.has(id)) {
            seen.add(id);
            out.push({ id, name });
        }
    });
    return out;
}

function filterChip(id, label, active) {
    const chip = el('button', 'filter-chip', label);
    chip.type = 'button';
    chip.dataset.id = id;
    chip.setAttribute('aria-pressed', String(active));
    chip.addEventListener('click', () => {
        if (id === state.projectClass) {
            return;
        }
        applyFromForm({ projectClass: id, force: true });
    });
    return chip;
}

function renderChips() {
    catChips.replaceChildren();
    catChips.appendChild(filterChip('', '全部', state.projectClass === ''));
    const seen = new Set();
    state.types.forEach(type => {
        if (seen.has(type.id)) {
            return;
        }
        seen.add(type.id);
        catChips.appendChild(filterChip(type.id, type.name, state.projectClass === type.id));
    });
    if (state.projectClass && !seen.has(state.projectClass)) {
        catChips.appendChild(filterChip(state.projectClass, '已选分类', true));
    }
}

function syncChips() {
    $$('.filter-chip', catChips).forEach(chip => {
        chip.setAttribute('aria-pressed', String((chip.dataset.id || '') === state.projectClass));
    });
}

function renderCityOptions() {
    citySelect.replaceChildren();
    const all = el('option', '', '全部城市');
    all.value = '';
    citySelect.appendChild(all);

    const seen = new Set();
    state.cities.forEach(city => {
        if (seen.has(city.id)) {
            return;
        }
        seen.add(city.id);
        const option = el('option', '', city.name);
        option.value = city.id;
        citySelect.appendChild(option);
    });
    if (state.city && !seen.has(state.city)) {
        const option = el('option', '', '已选城市');
        option.value = state.city;
        citySelect.appendChild(option);
    }
    citySelect.value = state.city;
}

function updateFilterNote() {
    filterNote.textContent = state.filterErrors.size ? FILTER_ERROR_NOTE : '';
}

function syncSummary() {
    const parts = [];
    if (state.projectClass) {
        const type = state.types.find(item => item.id === state.projectClass);
        parts.push('分类：' + (type ? type.name : '已选分类'));
    }
    if (state.city) {
        const city = state.cities.find(item => item.id === state.city);
        parts.push('城市：' + (city ? city.name : '已选城市'));
    }
    if (state.startTime || state.endTime) {
        parts.push('日期：' + (state.startTime || '不限') + ' 至 ' + (state.endTime || '不限'));
    }
    if (state.projectName) {
        parts.push('名称：' + state.projectName);
    }
    filterSummary.textContent = parts.length ? parts.join(' · ') : '按分类、城市和日期 3 种条件筛选';
    btnClear.disabled = parts.length === 0;
}

function syncControls() {
    const cityOption = [...citySelect.options].some(option => option.value === state.city);
    citySelect.value = cityOption ? state.city : '';
    startInput.value = state.startTime;
    endInput.value = state.endTime;
    searchInput.value = state.projectName;
    syncChips();
    syncSummary();
}

function readUrl() {
    const params = new URLSearchParams(location.search);
    state.projectClass = params.get('projectClass') || '';
    state.city = params.get('city') || '';
    state.startTime = toInputDate(params.get('startTime'));
    state.endTime = toInputDate(params.get('endTime'));
    state.projectName = (params.get('projectName') || '').trim();
    const page = Number.parseInt(params.get('page') || '1', 10);
    state.page = Number.isFinite(page) && page > 0 ? page : 1;
}

function writeUrl() {
    const params = new URLSearchParams();
    if (state.projectClass) {
        params.set('projectClass', state.projectClass);
    }
    if (state.city) {
        params.set('city', state.city);
    }
    if (state.startTime) {
        params.set('startTime', toQueryDate(state.startTime));
    }
    if (state.endTime) {
        params.set('endTime', toQueryDate(state.endTime));
    }
    if (state.projectName) {
        params.set('projectName', state.projectName);
    }
    if (state.page > 1) {
        params.set('page', String(state.page));
    }
    const url = new URL(location.href);
    url.search = params.toString();
    history.replaceState(null, '', url.pathname + url.search + url.hash);
}

function pageWindow(current, total, size) {
    const maxStart = Math.max(1, total - size + 1);
    let start = Math.max(1, current - Math.floor(size / 2));
    start = Math.min(start, maxStart);
    const end = Math.min(total, start + size - 1);
    const pages = [];
    for (let page = start; page <= end; page++) {
        pages.push(page);
    }
    return pages;
}

function renderSkeletons() {
    grid.hidden = false;
    emptyState.hidden = true;
    errorState.hidden = true;
    pager.hidden = true;
    grid.setAttribute('aria-busy', 'true');
    const cards = [];
    for (let index = 0; index < Math.min(6, state.pageSize); index++) {
        const card = el('div', 'card show-card show-card--skeleton m-nolift');
        card.setAttribute('aria-hidden', 'true');
        card.appendChild(el('div', 'skeleton show-card__poster-skeleton'));
        const body = el('div', 'show-card__body');
        body.appendChild(el('div', 'skeleton skeleton--line'));
        body.appendChild(el('div', 'skeleton skeleton--line'));
        body.appendChild(el('div', 'skeleton skeleton--line'));
        card.appendChild(body);
        cards.push(card);
    }
    grid.replaceChildren(...cards);
    listStatus.textContent = '正在读取演出…';
}

function renderError(message) {
    grid.hidden = true;
    grid.replaceChildren();
    emptyState.hidden = true;
    errorState.hidden = false;
    pager.hidden = true;
    errorMessage.textContent = message || '请稍后重试。';
    listStatus.textContent = '读取失败';
}

function renderPager() {
    if (state.totalPage <= 1 || state.totalRow === 0) {
        pager.hidden = true;
        return;
    }
    pager.hidden = false;
    prevPage.disabled = state.page <= 1;
    nextPage.disabled = state.page >= state.totalPage;
    const pages = pageWindow(state.page, state.totalPage, 5);
    pageNums.replaceChildren(...pages.map(page => {
        const button = el('button', 'pager__num', String(page));
        button.type = 'button';
        button.setAttribute('aria-label', '第 ' + page + ' 页');
        if (page === state.page) {
            button.classList.add('is-current');
            button.setAttribute('aria-current', 'page');
        }
        button.addEventListener('click', () => goPage(page));
        return button;
    }));
    pageMeta.textContent = '第 ' + state.page + ' / ' + state.totalPage + ' 页 · 共 ' + state.totalRow + ' 场';
}

function renderList(list) {
    emptyState.hidden = true;
    errorState.hidden = true;
    if (!list.length) {
        grid.hidden = true;
        grid.replaceChildren();
        pager.hidden = true;
        emptyState.hidden = false;
        listStatus.textContent = '没有结果';
        return;
    }
    grid.hidden = false;
    grid.replaceChildren(...list.map(showCard));
    listStatus.textContent = '共 ' + (state.totalRow || list.length) + ' 场演出';
    renderPager();
}

async function loadTypes() {
    try {
        const data = await api('project/type/list');
        state.types = flattenTypes(data).slice(0, CATEGORY_LIMIT);
    } catch (error) {
        state.types = [];
        state.filterErrors.add('分类');
    } finally {
        renderChips();
        updateFilterNote();
        syncSummary();
    }
}

async function loadCities() {
    try {
        const data = await api('project/city/list');
        state.cities = flattenCities(data);
    } catch (error) {
        state.cities = [];
        state.filterErrors.add('城市');
    } finally {
        renderCityOptions();
        updateFilterNote();
        syncSummary();
    }
}

async function loadHot() {
    try {
        const data = await api('project/hot');
        const list = data && Array.isArray(data.dataList) ? data.dataList : [];
        state.hotList = list.filter(item => item && item.projectToken);
        state.hotItem = state.hotList[0] || null;
    } catch (error) {
        state.hotList = [];
        state.hotItem = null;
    } finally {
        state.hotSettled = true;
        maybeRenderHero();
    }
}

/**
 * 首屏亮点避开当前页列表里已经出现的项目，免得同一张海报出现两次。
 * @param {Array} list 当前页的演出
 */
function chooseHero(list) {
    if (!state.hotList.length) {
        return;
    }
    const seen = new Set((list || []).map(item => String(item.projectToken)));
    state.hotItem = state.hotList.find(item => !seen.has(String(item.projectToken))) || state.hotList[0];
}

async function loadList() {
    const seq = ++state.listSeq;
    if (state.listController) {
        state.listController.abort();
    }
    const controller = new AbortController();
    state.listController = controller;
    state.listSettled = false;
    renderSkeletons();

    const query = { page: state.page, pageSize: state.pageSize };
    if (state.projectClass) {
        query.projectClass = state.projectClass;
    }
    if (state.city) {
        query.city = state.city;
    }
    if (state.startTime) {
        query.startTime = toQueryDate(state.startTime);
    }
    if (state.endTime) {
        query.endTime = toQueryDate(state.endTime);
    }
    if (state.projectName) {
        query.projectName = state.projectName;
    }
    const path = state.projectName ? 'project/language/list' : 'project/list';

    try {
        const data = await api(path, { query, signal: controller.signal });
        if (seq !== state.listSeq) {
            return;
        }
        const list = data && Array.isArray(data.dataList) ? data.dataList : [];
        const totalRow = Number(data && data.totalRow);
        state.totalRow = Number.isFinite(totalRow) && totalRow > 0 ? totalRow : list.length;
        const totalPage = Number(data && data.totalPage);
        state.totalPage = Number.isFinite(totalPage) && totalPage > 0
            ? totalPage
            : (state.totalRow ? Math.ceil(state.totalRow / state.pageSize) : 0);
        if (state.totalPage > 0 && state.page > state.totalPage) {
            state.page = state.totalPage;
            writeUrl();
            loadList();
            return;
        }
        state.listFirstItem = list[0] || null;
        state.listSettled = true;
        chooseHero(list);
        renderList(list);
        maybeRenderHero();
    } catch (error) {
        if (isAbort(error) || seq !== state.listSeq) {
            return;
        }
        state.listFirstItem = null;
        state.listSettled = true;
        renderError(error && error.message ? error.message : '请稍后重试。');
        maybeRenderHero();
    } finally {
        if (seq === state.listSeq) {
            grid.setAttribute('aria-busy', 'false');
        }
    }
}

function applyFromForm({ projectClass = state.projectClass, force = false } = {}) {
    const startTime = startInput.value;
    const endTime = endInput.value;
    if (startTime && endTime && startTime > endTime) {
        filterNote.textContent = '开始日期不能晚于结束日期。';
        startInput.focus();
        return;
    }
    const next = {
        projectClass,
        city: citySelect.value,
        startTime,
        endTime,
        projectName: searchInput.value.trim()
    };
    const changed = next.projectClass !== state.projectClass
        || next.city !== state.city
        || next.startTime !== state.startTime
        || next.endTime !== state.endTime
        || next.projectName !== state.projectName;
    if (!force && !changed && state.page === 1) {
        return;
    }

    state.projectClass = next.projectClass;
    state.city = next.city;
    state.startTime = next.startTime;
    state.endTime = next.endTime;
    state.projectName = next.projectName;
    state.page = 1;
    filterNote.textContent = state.filterErrors.size ? FILTER_ERROR_NOTE : '';
    syncControls();
    writeUrl();
    loadList();
}

function clearFilters() {
    state.projectClass = '';
    state.city = '';
    state.startTime = '';
    state.endTime = '';
    state.projectName = '';
    state.page = 1;
    filterNote.textContent = state.filterErrors.size ? FILTER_ERROR_NOTE : '';
    syncControls();
    writeUrl();
    loadList();
}

function goPage(page) {
    if (!Number.isFinite(page) || page < 1 || page > state.totalPage || page === state.page) {
        return;
    }
    state.page = page;
    writeUrl();
    loadList();
    listSection.scrollIntoView({ behavior: state.reduced ? 'auto' : 'smooth', block: 'start' });
    listHeading.focus({ preventScroll: true });
}

filterForm.addEventListener('submit', event => {
    event.preventDefault();
    applyFromForm();
});

citySelect.addEventListener('change', () => applyFromForm());
startInput.addEventListener('change', () => applyFromForm());
endInput.addEventListener('change', () => applyFromForm());
searchInput.addEventListener('change', () => applyFromForm());
searchInput.addEventListener('search', () => applyFromForm());

btnClear.addEventListener('click', clearFilters);
btnEmptyClear.addEventListener('click', clearFilters);
btnRetry.addEventListener('click', () => loadList());
prevPage.addEventListener('click', () => goPage(state.page - 1));
nextPage.addEventListener('click', () => goPage(state.page + 1));

function init() {
    readUrl();
    syncControls();
    renderSkeletons();
    loadTypes();
    loadCities();
    loadHot();
    loadList();
}

init();
