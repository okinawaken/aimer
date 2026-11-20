import { api } from './aliyu-api.js';
import {
    $,
    $$,
    toast,
    maskPhone,
    maskSecret,
    formatTime,
    setBusy,
    showFieldError,
    clearFieldError,
    confirmInline
} from './aliyu-ui.js';

const state = {
    bound: false,
    checked: false,
    config: null,
    userId: null,
    profile: null,
    loadSeq: 0
};

const sessionInput = $('#session');
const sessionError = $('#session-error');
const form = $('#credForm');
const formStatus = $('#formStatus');
const formActions = $('#formActions');
const saveBtn = $('#btnSave');
const checkBtn = $('#btnCheck');
const unbindBtn = $('#btnUnbind');
const boundBody = $('#boundBody');
const boundState = $('#boundState');

/**
 * 设置表单状态行。
 * @param {string} text 文案
 * @param {''|'ok'|'err'} kind 状态
 */
function setFormStatus(text, kind = '') {
    formStatus.textContent = text;
    formStatus.classList.toggle('is-ok', kind === 'ok');
    formStatus.classList.toggle('is-err', kind === 'err');
}

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

function kvRow(label, value, mono = false) {
    const row = el('div', 'kv-row');
    row.appendChild(el('span', 'k', label));
    row.appendChild(el('span', 'v' + (mono ? ' mono' : ''), value));
    return row;
}

function renderSkeleton() {
    boundBody.replaceChildren();
    const identity = el('div', 'bound-identity');
    identity.appendChild(el('div', 'skeleton skeleton--avatar'));
    const lines = el('div');
    lines.style.flex = '1';
    lines.appendChild(el('div', 'skeleton skeleton--line'));
    lines.appendChild(el('div', 'skeleton skeleton--line'));
    identity.appendChild(lines);
    boundBody.appendChild(identity);
    const kv = el('div', 'kv');
    for (let i = 0; i < 3; i++) {
        kv.appendChild(el('div', 'skeleton skeleton--line'));
    }
    boundBody.appendChild(kv);
}

function renderUnbound() {
    boundState.textContent = '未绑定';
    boundBody.replaceChildren();
    const empty = el('div', 'empty');
    const icon = el('div', 'empty__icon');
    icon.innerHTML = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M12 3a5 5 0 0 0-5 5v3H6a1 1 0 0 0-1 1v7a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-7a1 1 0 0 0-1-1h-1V8a5 5 0 0 0-5-5Z"/><path d="M9 8a3 3 0 0 1 6 0v3H9V8Z"/></svg>';
    empty.appendChild(icon);
    empty.appendChild(el('h3', '', '还没有绑定阿里鱼凭证'));
    empty.appendChild(el('p', '', '绑定后才能读取演出、订单和会员信息。从已登录的小程序请求头复制 X-MZ-SESSION。'));
    const focusBtn = el('button', 'btn btn--secondary', '粘贴凭证');
    focusBtn.type = 'button';
    focusBtn.addEventListener('click', () => {
        sessionInput.focus();
        sessionInput.scrollIntoView({ block: 'center', behavior: 'smooth' });
    });
    empty.appendChild(focusBtn);
    boundBody.appendChild(empty);
}

function renderBound() {
    const profile = state.profile || {};
    const name = profile.userName || '已绑定账号';
    boundState.textContent = state.checked ? '本次会话已校验' : '已保存';

    boundBody.replaceChildren();
    const identity = el('div', 'bound-identity');
    identity.appendChild(el('div', 'avatar', String(name).trim().slice(0, 1) || '鱼'));
    const meta = el('div');
    meta.appendChild(el('div', 'name', name));
    meta.appendChild(el('div', 'sub', maskPhone(profile.userTel)));
    identity.appendChild(meta);
    boundBody.appendChild(identity);

    const kv = el('div', 'kv');
    kv.appendChild(kvRow('系统用户 ID', state.userId === null || state.userId === undefined ? '—' : String(state.userId)));
    const session = state.config && state.config.session ? state.config.session : '';
    kv.appendChild(kvRow('X-MZ-SESSION', maskSecret(session), true));
    kv.appendChild(kvRow('最近登录', formatTime(profile.lastLoginTime)));
    boundBody.appendChild(kv);
}

function renderBoundWarning(message) {
    boundState.textContent = '上游异常';
    boundBody.replaceChildren();
    const identity = el('div', 'bound-identity');
    identity.appendChild(el('div', 'avatar', '!'));
    const meta = el('div');
    meta.appendChild(el('div', 'name', '凭证已保存'));
    meta.appendChild(el('div', 'sub', '上游没有返回账号信息'));
    identity.appendChild(meta);
    boundBody.appendChild(identity);

    const note = el('p', 'hint', message || '可以重新校验凭证，或粘贴新的 X-MZ-SESSION 换绑。');
    boundBody.appendChild(note);

    const kv = el('div', 'kv');
    const session = state.config && state.config.session ? state.config.session : '';
    kv.appendChild(kvRow('X-MZ-SESSION', maskSecret(session), true));
    boundBody.appendChild(kv);
}

function renderLoadError(message) {
    boundState.textContent = '读取失败';
    boundBody.replaceChildren();
    const empty = el('div', 'empty');
    empty.appendChild(el('h3', '', '没有读到当前配置'));
    empty.appendChild(el('p', '', message || '稍后重试。'));
    const retry = el('button', 'btn btn--secondary', '重新加载');
    retry.type = 'button';
    retry.addEventListener('click', () => load());
    empty.appendChild(retry);
    boundBody.appendChild(empty);
}

function syncActions() {
    unbindBtn.hidden = !state.bound;
    checkBtn.disabled = false;
    saveBtn.disabled = false;
}

async function load() {
    const seq = ++state.loadSeq;
    renderSkeleton();
    setFormStatus('正在读取当前绑定…');
    try {
        const data = await api('user-setting/me');
        if (seq !== state.loadSeq) {
            return;
        }
        state.config = (data && data.config) || {};
        state.userId = data ? data.userId : null;
        state.bound = !!(state.config.session && String(state.config.session).trim());
        state.checked = false;
        state.profile = null;
        syncActions();

        if (!state.bound) {
            renderUnbound();
            setFormStatus('还没有绑定凭证。粘贴 X-MZ-SESSION 后保存即可。');
            return;
        }

        setFormStatus('已读取当前绑定。');
        try {
            const profile = await api('user/info', { query: { langType: 1 } });
            if (seq !== state.loadSeq) {
                return;
            }
            state.profile = profile || null;
            renderBound();
        } catch (e) {
            if (seq !== state.loadSeq) {
                return;
            }
            renderBoundWarning(e.message);
        }
    } catch (e) {
        if (seq !== state.loadSeq) {
            return;
        }
        renderLoadError(e.message);
        setFormStatus(e.message, 'err');
    }
}

function validate() {
    let ok = true;
    const session = sessionInput.value.trim();
    clearFieldError(sessionInput, sessionError);

    if (!session) {
        showFieldError(sessionInput, sessionError, 'X-MZ-SESSION 不能为空。');
        ok = false;
    } else if (session.length > 128) {
        showFieldError(sessionInput, sessionError, 'X-MZ-SESSION 不能超过 128 个字符。');
        ok = false;
    }
    if (!ok) {
        const first = $('.field.is-invalid input, .field.is-invalid textarea');
        if (first) {
            first.focus();
        }
    }
    return { ok, session };
}

async function save() {
    const { ok, session } = validate();
    if (!ok) {
        setFormStatus('有字段需要修改。', 'err');
        return;
    }
    setBusy(saveBtn, true, '绑定中…');
    checkBtn.disabled = true;
    setFormStatus('正在请求上游校验并保存…');
    try {
        await api('user-setting/me', {
            method: 'PUT',
            body: { session }
        });
        state.checked = true;
        sessionInput.value = '';
        setFormStatus('已保存，凭证通过上游校验。', 'ok');
        toast('已保存并绑定');
        await load();
        if (state.bound && state.profile) {
            state.checked = true;
            boundState.textContent = '本次会话已校验';
        }
    } catch (e) {
        setFormStatus(e.message, 'err');
        toast(e.message, false);
    } finally {
        setBusy(saveBtn, false);
        checkBtn.disabled = false;
    }
}

async function check() {
    const { ok, session } = validate();
    if (!ok) {
        setFormStatus('有字段需要修改。', 'err');
        return;
    }
    setBusy(checkBtn, true, '校验中…');
    saveBtn.disabled = true;
    setFormStatus('正在请求上游校验…');
    try {
        await api('user-setting/check', {
            method: 'POST',
            body: { session }
        });
        state.checked = true;
        if (state.bound) {
            boundState.textContent = '本次会话已校验';
        }
        setFormStatus('凭证有效，可以保存绑定。', 'ok');
        toast('凭证有效');
    } catch (e) {
        setFormStatus(e.message, 'err');
        toast(e.message, false);
    } finally {
        setBusy(checkBtn, false);
        saveBtn.disabled = false;
    }
}

function unbind() {
    confirmInline(formActions, {
        message: '解绑后自动任务会停止，服务端保存的会话缓存也会清除。确认解绑？',
        confirmLabel: '确认解绑',
        onConfirm: async button => {
            setBusy(button, true, '解绑中…');
            setFormStatus('正在解绑…');
            try {
                await api('user-setting/me', { method: 'DELETE' });
                state.checked = false;
                setFormStatus('已解绑，凭证已从服务端清除。', 'ok');
                toast('已解绑');
                await load();
            } catch (e) {
                setFormStatus(e.message, 'err');
                toast(e.message, false);
            }
        }
    });
}

function bindReveal(button) {
    const target = document.getElementById(button.dataset.target);
    if (!target) {
        return;
    }
    const eye = $('[data-eye]', button);
    const eyeOff = $('[data-eye-off]', button);
    button.addEventListener('click', () => {
        const show = target.type === 'password';
        target.type = show ? 'text' : 'password';
        button.setAttribute('aria-pressed', String(show));
        button.setAttribute('aria-label', show ? '隐藏内容' : '显示内容');
        if (eye && eyeOff) {
            eye.hidden = show;
            eyeOff.hidden = !show;
        }
        target.focus();
    });
}

form.addEventListener('submit', event => {
    event.preventDefault();
    save();
});

checkBtn.addEventListener('click', check);
unbindBtn.addEventListener('click', unbind);

sessionInput.addEventListener('input', () => {
    clearFieldError(sessionInput, sessionError);
    if (formStatus.classList.contains('is-err')) {
        setFormStatus('');
    }
});

$$('.icon-btn[data-target]').forEach(bindReveal);

load();
