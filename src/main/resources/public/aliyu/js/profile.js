import { api, tplUrl } from './aliyu-api.js';
import {
    $,
    $$,
    toast,
    maskPhone,
    formatTime,
    setBusy,
    showFieldError,
    clearFieldError
} from './aliyu-ui.js';

const state = {
    bound: false,
    profile: null,
    login: null,
    contact: null,
    member: null,
    counted: false,
    loadSeq: 0
};

const stateSection = $('#stateSection');
const stateCard = $('#stateCard');
const dataSections = $$('[data-profile-section]');
const identityBody = $('#identityBody');
const identityState = $('#identityState');
const basicBody = $('#basicBody');
const btnEdit = $('#btnEdit');
const benefitsSection = $('#benefitsSection');
const benefitsSub = $('#benefitsSub');
const benefitGrid = $('#benefitGrid');

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

function firstChar(text) {
    return String(text || '').trim().slice(0, 1) || '鱼';
}

function reduced() {
    return matchMedia('(prefers-reduced-motion: reduce)').matches;
}

function headless() {
    return /HeadlessChrome/.test(navigator.userAgent);
}

function showData(show) {
    dataSections.forEach(section => {
        section.hidden = !show;
    });
    stateSection.hidden = show;
}

function formatSex(value) {
    return Number(value) === 1 ? '男' : Number(value) === 2 ? '女' : '未设置';
}

function formatValue(value, fallback = '—') {
    return value === null || value === undefined || value === '' ? fallback : String(value);
}

function normalizeDate(value) {
    const text = String(value || '').trim();
    if (!text) {
        return '';
    }
    const iso = text.match(/^(\d{4})[-/](\d{2})[-/](\d{2})/);
    if (iso) {
        return iso[1] + '-' + iso[2] + '-' + iso[3];
    }
    const date = new Date(text);
    if (Number.isNaN(date.getTime())) {
        return '';
    }
    const pad = n => String(n).padStart(2, '0');
    return date.getFullYear() + '-' + pad(date.getMonth() + 1) + '-' + pad(date.getDate());
}

function todayString() {
    const date = new Date();
    const pad = n => String(n).padStart(2, '0');
    return date.getFullYear() + '-' + pad(date.getMonth() + 1) + '-' + pad(date.getDate());
}

function kvRow(label, value, mono = false) {
    const row = el('div', 'kv-row');
    row.appendChild(el('span', 'k', label));
    row.appendChild(el('span', 'v' + (mono ? ' mono' : ''), value));
    return row;
}

function countUp(node, text) {
    if (reduced() || headless()) {
        return;
    }
    const match = String(text).match(/-?\d[\d,]*(?:\.\d+)?/);
    if (!match) {
        return;
    }
    const raw = match[0];
    const target = Number(raw.replace(/,/g, ''));
    if (!Number.isFinite(target) || target === 0) {
        return;
    }
    const decimals = (raw.split('.')[1] || '').length;
    const prefix = text.slice(0, match.index);
    const suffix = text.slice(match.index + raw.length);
    const start = performance.now();
    const duration = 1100;
    const frame = now => {
        const progress = Math.min(1, (now - start) / duration);
        const eased = 1 - Math.pow(1 - progress, 3);
        const value = target * eased;
        const formatted = decimals ? value.toFixed(decimals) : String(Math.round(value));
        node.textContent = prefix + formatted + suffix;
        if (progress < 1) {
            requestAnimationFrame(frame);
        } else {
            node.textContent = text;
        }
    };
    requestAnimationFrame(frame);
}

function stat(label, value, unit, count) {
    const box = el('div', 'stat');
    box.appendChild(el('div', 'stat__label', label));
    const line = el('div', 'stat__line');
    const valueNode = el('div', 'stat__value');
    const text = formatValue(value);
    valueNode.textContent = text;
    line.appendChild(valueNode);
    if (unit && text !== '—') {
        line.appendChild(el('span', 'stat__unit', unit));
    }
    box.appendChild(line);
    if (count && text !== '—') {
        countUp(valueNode, text);
    }
    return box;
}

function renderSkeletons() {
    identityState.textContent = '读取中';
    identityBody.replaceChildren();
    const identity = el('div', 'identity');
    identity.appendChild(el('div', 'skeleton skeleton--avatar'));
    const lines = el('div', 'identity__body');
    lines.style.flex = '1';
    lines.appendChild(el('div', 'skeleton skeleton--line'));
    lines.appendChild(el('div', 'skeleton skeleton--line'));
    identity.appendChild(lines);
    identityBody.appendChild(identity);

    basicBody.replaceChildren();
    for (let i = 0; i < 3; i++) {
        basicBody.appendChild(el('div', 'skeleton skeleton--line'));
    }

    benefitsSection.hidden = true;
}

function renderIdentity() {
    const profile = state.profile || {};
    const login = state.login || {};
    const contact = state.contact || {};
    const name = profile.userName || login.userName || '阿里鱼用户';
    const phone = contact.userTel || profile.userTel || login.userTel || '';
    const level = profile.levelName || profile.rechargeLevelName || '';
    const lastLogin = profile.lastLoginTime || login.lastLoginTime || '';
    const count = !state.counted;

    identityState.textContent = state.profile ? '已同步' : '部分资料缺失';
    identityBody.replaceChildren();

    const identity = el('div', 'identity');
    const avatar = el('div', 'avatar');
    if (profile.userPhoto) {
        avatar.classList.add('avatar--photo');
        const img = document.createElement('img');
        img.src = profile.userPhoto;
        img.alt = '';
        img.width = 104;
        img.height = 104;
        img.loading = 'lazy';
        img.decoding = 'async';
        img.referrerPolicy = 'no-referrer';
        img.addEventListener('error', () => {
            avatar.classList.remove('avatar--photo');
            avatar.textContent = firstChar(name);
        });
        avatar.appendChild(img);
    } else {
        avatar.textContent = firstChar(name);
    }
    identity.appendChild(avatar);

    const body = el('div', 'identity__body');
    body.appendChild(el('div', 'identity__name', name));
    const meta = el('div', 'identity__meta');
    if (level) {
        meta.appendChild(el('span', 'chip chip--solid', level));
    }
    meta.appendChild(el('span', '', maskPhone(phone)));
    meta.appendChild(el('span', '', '最近登录 ' + formatTime(lastLogin)));
    body.appendChild(meta);
    identity.appendChild(body);
    identityBody.appendChild(identity);

    const stats = el('div', 'stats');
    stats.appendChild(stat('小鱼丸', profile.point, '点', count));
    stats.appendChild(stat('成长值', profile.growthValue, '', count));
    stats.appendChild(stat('账户余额', profile.accountMoney, '元', count));
    identityBody.appendChild(stats);
    state.counted = true;

    if (!state.profile) {
        identityBody.appendChild(el('p', 'hint', '详细资料暂时没有返回，可以重新加载，或先去用户设置校验凭证。'));
    }
}

function findCurrentLevel(levels, member) {
    const sorted = [...levels].sort((a, b) => Number(a.level || 0) - Number(b.level || 0));
    const flag = Number(member.memberFlag ?? member.memberLevel ?? member.level);
    if (Number.isFinite(flag)) {
        const hit = sorted.find(item => Number(item.level) === flag);
        if (hit) {
            return hit;
        }
    }
    const experience = Number(member.experience || 0);
    let current = null;
    sorted.forEach(item => {
        if (Number(item.upgradeThreshold || 0) <= experience) {
            current = item;
        }
    });
    return current || sorted[0] || null;
}

function collectBenefits(member, levels, current) {
    let source = [];
    if (current && Array.isArray(current.cardBenefitList) && current.cardBenefitList.length) {
        source = current.cardBenefitList;
    } else if (Array.isArray(member.cardBenefitList) && member.cardBenefitList.length) {
        source = member.cardBenefitList;
    } else {
        levels.forEach(item => {
            if (Array.isArray(item.cardBenefitList)) {
                source = source.concat(item.cardBenefitList);
            }
        });
    }
    const seen = new Set();
    const out = [];
    source.forEach(benefit => {
        const name = benefit.ruleName || benefit.benefitName || '';
        if (!name || seen.has(name)) {
            return;
        }
        seen.add(name);
        out.push({
            name,
            desc: benefit.benefitDescription || '',
            icon: benefit.benefitIcon || benefit.ruleIcon || ''
        });
    });
    return out;
}

function renderBenefit(benefit) {
    const card = el('article', 'benefit m-nolift');
    if (benefit.icon) {
        const img = document.createElement('img');
        img.className = 'benefit__icon';
        img.src = benefit.icon;
        img.alt = '';
        img.width = 40;
        img.height = 40;
        img.loading = 'lazy';
        img.decoding = 'async';
        img.referrerPolicy = 'no-referrer';
        img.addEventListener('error', () => img.remove());
        card.appendChild(img);
    } else {
        const mark = el('span', 'benefit__mark');
        mark.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linejoin="round" aria-hidden="true"><path d="m12 3 2.6 5.6 6.1.7-4.5 4.2 1.2 6-5.4-3-5.4 3 1.2-6L3.3 9.3l6.1-.7Z"/></svg>';
        card.appendChild(mark);
    }
    card.appendChild(el('div', 'benefit__name', benefit.name));
    if (benefit.desc) {
        card.appendChild(el('p', 'benefit__desc', benefit.desc));
    }
    return card;
}

function renderMember() {
    const member = state.member;
    if (!member) {
        benefitsSection.hidden = true;
        return;
    }

    const cards = Array.isArray(member.cartInfoList) ? member.cartInfoList : [];
    const mainCard = cards[0];
    const levels = mainCard && Array.isArray(mainCard.memberLevels) && mainCard.memberLevels.length
        ? mainCard.memberLevels
        : (Array.isArray(member.memberLevels) ? member.memberLevels : []);
    const current = findCurrentLevel(levels, member);
    const benefits = collectBenefits(member, levels, current);

    if (benefits.length) {
        benefitGrid.dataset.count = String(Math.min(benefits.length, 9));
        benefitsSection.hidden = false;
        benefitsSub.textContent = current
            ? '来自 ' + (current.levelName || current.levelCode || '当前等级')
            : '来自当前会员等级';
        benefitGrid.replaceChildren(...benefits.slice(0, 9).map(renderBenefit));
    } else {
        benefitsSection.hidden = true;
    }
}

function renderBasic() {
    const profile = state.profile || {};
    const login = state.login || {};
    const rows = [
        ['昵称', profile.userName || login.userName || '—'],
        ['性别', formatSex(profile.userSex)],
        ['生日', formatValue(profile.userBirthday)],
        ['手机号', maskPhone(profile.userTel || (state.contact && state.contact.userTel) || login.userTel)]
    ];

    basicBody.replaceChildren();
    const list = el('dl', 'profile-rows');
    rows.forEach(([label, value]) => {
        const row = el('div', 'profile-row');
        row.appendChild(el('dt', '', label));
        row.appendChild(el('dd', '', value || '—'));
        list.appendChild(row);
    });
    basicBody.appendChild(list);

    const canEdit = !!state.profile && !state.profile.forbidAlterInformation;
    btnEdit.hidden = !canEdit;
    if (!state.profile) {
        basicBody.appendChild(el('p', 'hint', '详细资料没有返回，暂时不能编辑。'));
    } else if (state.profile.forbidAlterInformation) {
        basicBody.appendChild(el('p', 'hint', '资料已锁定，需要在小程序内修改。'));
    }
}

function renderUnbound() {
    stateCard.replaceChildren();
    const empty = el('div', 'empty');
    const icon = el('div', 'empty__icon');
    icon.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M12 3a5 5 0 0 0-5 5v3H6a1 1 0 0 0-1 1v7a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-7a1 1 0 0 0-1-1h-1V8a5 5 0 0 0-5-5Z"/><path d="M9 8a3 3 0 0 1 6 0v3H9V8Z"/></svg>';
    empty.appendChild(icon);
    empty.appendChild(el('h2', '', '尚未绑定阿里鱼凭证'));
    empty.appendChild(el('p', '', '绑定后才能读取会员等级、小鱼丸、成长值和基本资料。'));
    const link = el('a', 'btn btn--primary', '打开用户设置');
    link.href = tplUrl('user-setting');
    empty.appendChild(link);
    stateCard.appendChild(empty);
}

function renderError(message) {
    stateCard.replaceChildren();
    const empty = el('div', 'empty');
    empty.appendChild(el('h2', '', '没有读到阿里鱼资料'));
    empty.appendChild(el('p', '', message || '稍后重试。'));
    const retry = el('button', 'btn btn--secondary', '重新加载');
    retry.type = 'button';
    retry.addEventListener('click', () => load());
    empty.appendChild(retry);
    const link = el('a', 'btn btn--text', '打开用户设置');
    link.href = tplUrl('user-setting');
    empty.appendChild(link);
    stateCard.appendChild(empty);
}

function fieldText(id, label, value, maxlength, hint) {
    const field = el('div', 'field');
    const labelNode = el('label', '', label);
    labelNode.htmlFor = id;
    field.appendChild(labelNode);

    const input = document.createElement('input');
    input.id = id;
    input.type = 'text';
    input.value = value;
    input.maxLength = maxlength;
    input.required = true;
    input.autocomplete = 'nickname';
    field.appendChild(input);

    const hintNode = el('p', 'hint', hint);
    hintNode.id = id + '-hint';
    field.appendChild(hintNode);

    const errorNode = el('p', 'field-error');
    errorNode.id = id + '-error';
    errorNode.hidden = true;
    field.appendChild(errorNode);

    input.setAttribute('aria-describedby', hintNode.id + ' ' + errorNode.id);
    return field;
}

function fieldSelect(id, label, sex) {
    const options = [['', '未设置'], ['1', '男'], ['2', '女']];
    const initial = Number(sex) === 1 || Number(sex) === 2 ? String(sex) : '';
    const field = el('div', 'field');
    const labelNode = el('label', '', label);
    labelNode.htmlFor = id + '-trigger';
    field.appendChild(labelNode);

    const hidden = document.createElement('input');
    hidden.type = 'hidden';
    hidden.id = id;
    hidden.value = initial;

    const wrapper = el('div', 'select');
    const trigger = el('button', 'select__trigger');
    trigger.type = 'button';
    trigger.id = id + '-trigger';
    trigger.setAttribute('role', 'combobox');
    trigger.setAttribute('aria-autocomplete', 'none');
    trigger.setAttribute('aria-haspopup', 'listbox');
    trigger.setAttribute('aria-expanded', 'false');
    trigger.setAttribute('aria-controls', id + '-list');

    const valueNode = el('span', 'select__value');
    trigger.appendChild(valueNode);
    const chevron = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    chevron.setAttribute('class', 'select__chevron');
    chevron.setAttribute('viewBox', '0 0 12 8');
    chevron.setAttribute('aria-hidden', 'true');
    chevron.innerHTML = '<path d="M1 1.5 6 6.5l5-5" fill="none" stroke="currentColor" '
        + 'stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"/>';
    trigger.appendChild(chevron);

    const list = el('ul', 'select__list');
    list.id = id + '-list';
    list.setAttribute('role', 'listbox');
    list.setAttribute('tabindex', '-1');
    list.hidden = true;

    const optionNodes = options.map(([value, text], index) => {
        const option = el('li', 'select__option', text);
        option.id = id + '-option-' + index;
        option.setAttribute('role', 'option');
        option.dataset.value = value;
        option.addEventListener('click', () => {
            selectOption(value);
            closeSelect();
            trigger.focus();
        });
        list.appendChild(option);
        return option;
    });

    let activeIndex = Math.max(0, options.findIndex(([value]) => value === initial));

    function syncValue() {
        const current = hidden.value;
        const hit = options.find(([value]) => value === current);
        valueNode.textContent = hit ? hit[1] : '未设置';
        optionNodes.forEach(option => {
            option.setAttribute('aria-selected', String(option.dataset.value === current));
        });
    }

    function setActive(index) {
        activeIndex = (index + options.length) % options.length;
        optionNodes.forEach((option, i) => option.classList.toggle('is-active', i === activeIndex));
        const active = optionNodes[activeIndex];
        trigger.setAttribute('aria-activedescendant', active.id);
        active.scrollIntoView({ block: 'nearest' });
    }

    function openSelect() {
        if (!list.hidden) {
            return;
        }
        list.hidden = false;
        wrapper.classList.add('is-open');
        trigger.setAttribute('aria-expanded', 'true');
        setActive(optionNodes.findIndex(option => option.dataset.value === hidden.value));
        document.addEventListener('pointerdown', onOutside, true);
    }

    function closeSelect() {
        if (list.hidden) {
            return;
        }
        list.hidden = true;
        wrapper.classList.remove('is-open');
        trigger.setAttribute('aria-expanded', 'false');
        trigger.removeAttribute('aria-activedescendant');
        document.removeEventListener('pointerdown', onOutside, true);
    }

    function selectOption(value) {
        hidden.value = value;
        syncValue();
        hidden.dispatchEvent(new Event('change', { bubbles: true }));
    }

    function onOutside(event) {
        if (!wrapper.contains(event.target)) {
            closeSelect();
        }
    }

    trigger.addEventListener('click', () => {
        if (list.hidden) {
            openSelect();
        } else {
            closeSelect();
        }
    });
    trigger.addEventListener('keydown', event => {
        if (event.key === 'ArrowDown' || event.key === 'ArrowUp') {
            event.preventDefault();
            if (list.hidden) {
                openSelect();
            }
            setActive(activeIndex + (event.key === 'ArrowDown' ? 1 : -1));
        } else if (event.key === 'Enter' || event.key === ' ') {
            event.preventDefault();
            if (list.hidden) {
                openSelect();
            } else {
                selectOption(optionNodes[activeIndex].dataset.value);
                closeSelect();
            }
        } else if (event.key === 'Escape' || event.key === 'Tab') {
            closeSelect();
        }
    });

    syncValue();
    wrapper.appendChild(trigger);
    wrapper.appendChild(list);
    field.appendChild(hidden);
    field.appendChild(wrapper);
    return field;
}

function fieldDate(id, label, value) {
    const field = el('div', 'field');
    const labelNode = el('label', '', label);
    labelNode.htmlFor = id;
    field.appendChild(labelNode);

    const input = document.createElement('input');
    input.id = id;
    input.type = 'date';
    input.value = normalizeDate(value);
    input.max = todayString();
    input.required = true;
    field.appendChild(input);

    const errorNode = el('p', 'field-error');
    errorNode.id = id + '-error';
    errorNode.hidden = true;
    field.appendChild(errorNode);
    input.setAttribute('aria-describedby', errorNode.id);
    return field;
}

function startEdit() {
    if (!state.profile || state.profile.forbidAlterInformation) {
        return;
    }
    const profile = state.profile;
    btnEdit.hidden = true;
    basicBody.replaceChildren();

    const form = document.createElement('form');
    form.className = 'edit-form';
    form.noValidate = true;

    const grid = el('div', 'edit-grid');
    grid.appendChild(fieldText('editName', '昵称', profile.userName || '', 20, '必填，最长 20 字符。'));
    grid.appendChild(fieldSelect('editSex', '性别', profile.userSex));
    grid.appendChild(fieldDate('editBirthday', '生日', profile.userBirthday || ''));
    form.appendChild(grid);

    const actions = el('div', 'form-actions');
    const save = el('button', 'btn btn--primary', '保存资料');
    save.type = 'submit';
    const cancel = el('button', 'btn btn--secondary', '取消');
    cancel.type = 'button';
    cancel.addEventListener('click', () => {
        renderBasic();
    });
    actions.appendChild(save);
    actions.appendChild(cancel);
    form.appendChild(actions);

    const status = el('p', 'form-status');
    status.id = 'editStatus';
    status.setAttribute('role', 'status');
    status.setAttribute('aria-live', 'polite');
    form.appendChild(status);

    form.addEventListener('submit', event => {
        event.preventDefault();
        saveProfile(save, status);
    });
    basicBody.appendChild(form);

    const nameInput = $('#editName');
    if (nameInput) {
        nameInput.focus();
    }
}

async function saveProfile(button, status) {
    const nameInput = $('#editName');
    const sexInput = $('#editSex');
    const birthdayInput = $('#editBirthday');
    if (!nameInput || !sexInput || !birthdayInput) {
        return;
    }

    const nameError = $('#editName-error');
    const birthdayError = $('#editBirthday-error');
    clearFieldError(nameInput, nameError);
    clearFieldError(birthdayInput, birthdayError);

    const name = nameInput.value.trim();
    const sexValue = sexInput.value;
    const birthday = birthdayInput.value;
    let ok = true;

    if (!name) {
        showFieldError(nameInput, nameError, '昵称不能为空。');
        ok = false;
    } else if (name.length > 20) {
        showFieldError(nameInput, nameError, '昵称不能超过 20 个字符。');
        ok = false;
    }
    if (!birthday) {
        showFieldError(birthdayInput, birthdayError, '选择生日。');
        ok = false;
    } else if (birthday > todayString()) {
        showFieldError(birthdayInput, birthdayError, '生日不能晚于今天。');
        ok = false;
    }
    if (!ok) {
        status.textContent = '有字段需要修改。';
        status.classList.add('is-err');
        return;
    }

    const profile = state.profile || {};
    const payload = {};
    if (name !== (profile.userName || '')) {
        payload.userName = name;
    }
    const currentSex = Number(profile.userSex) === 1 || Number(profile.userSex) === 2
        ? String(profile.userSex) : '';
    if (sexValue !== currentSex && sexValue !== '') {
        payload.userSex = Number(sexValue);
    }
    if (birthday !== normalizeDate(profile.userBirthday)) {
        payload.userBirthday = birthday;
    }
    if (!Object.keys(payload).length) {
        status.textContent = '没有改动。';
        status.classList.remove('is-err');
        return;
    }

    setBusy(button, true, '保存中…');
    status.textContent = '正在保存…';
    status.classList.remove('is-err');
    try {
        await api('user/modify', { method: 'POST', body: payload });
        toast('资料已保存');
        const info = await api('user/info', { query: { langType: 1 } });
        if (info) {
            state.profile = info;
        }
        renderBasic();
        renderIdentity();
    } catch (e) {
        status.textContent = e.message;
        status.classList.add('is-err');
        toast(e.message, false);
    } finally {
        setBusy(button, false);
    }
}

function renderAll() {
    showData(true);
    renderIdentity();
    renderMember();
    renderBasic();
}

async function load() {
    const seq = ++state.loadSeq;
    state.counted = false;
    showData(true);
    renderSkeletons();
    btnEdit.hidden = true;

    try {
        const setting = await api('user-setting/me');
        if (seq !== state.loadSeq) {
            return;
        }
        state.bound = !!(setting && setting.config && setting.config.session);
        if (!state.bound) {
            showData(false);
            renderUnbound();
            return;
        }

        const [loginResult, infoResult, contactResult, memberResult] = await Promise.allSettled([
            api('user/login-user'),
            api('user/info', { query: { langType: 1 } }),
            api('user/contact'),
            api('member-point/member-info')
        ]);
        if (seq !== state.loadSeq) {
            return;
        }

        state.login = loginResult.status === 'fulfilled' ? loginResult.value : null;
        state.profile = infoResult.status === 'fulfilled' ? infoResult.value : null;
        state.contact = contactResult.status === 'fulfilled' ? contactResult.value : null;
        state.member = memberResult.status === 'fulfilled' ? memberResult.value : null;

        if (!state.profile && !state.login) {
            showData(false);
            renderError('上游没有返回账号资料。可以重新加载，或先去用户设置校验凭证。');
            return;
        }
        renderAll();
    } catch (e) {
        if (seq !== state.loadSeq) {
            return;
        }
        showData(false);
        renderError(e.message || '读取失败，稍后重试。');
    }
}

btnEdit.addEventListener('click', startEdit);

load();
