/**
 * 阿里鱼页面公共 UI：轻提示、掩码、格式化、按钮忙碌态、字段错误与内联确认。
 */

const $ = (selector, root = document) => root.querySelector(selector);
const $$ = (selector, root = document) => [...root.querySelectorAll(selector)];

/**
 * 右下角轻提示。
 * @param {string} message 文案
 * @param {boolean} ok 是否成功
 */
function toast(message, ok = true) {
    let stack = $('.toast-stack');
    if (!stack) {
        stack = document.createElement('div');
        stack.className = 'toast-stack';
        stack.setAttribute('aria-live', 'polite');
        document.body.appendChild(stack);
    }
    const item = document.createElement('div');
    item.className = 'toast' + (ok ? '' : ' is-err');
    item.setAttribute('role', 'status');
    item.textContent = message;
    stack.appendChild(item);
    setTimeout(() => item.remove(), 2800);
}

/**
 * 掩码显示秘密：保留头尾各 4 位，中间省略。
 * @param {string} value 原文
 * @returns {string} 掩码文本
 */
function maskSecret(value) {
    const text = String(value || '');
    if (!text) {
        return '未保存';
    }
    if (text.length <= 12) {
        return '已保存 · ' + text.length + ' 字符';
    }
    return text.slice(0, 4) + '…' + text.slice(-4);
}

/**
 * 手机号掩码：138****8000。
 * @param {string} value 手机号
 * @returns {string} 掩码文本
 */
function maskPhone(value) {
    const text = String(value || '').trim();
    if (!text) {
        return '未返回手机号';
    }
    if (text.length < 7) {
        return text;
    }
    return text.slice(0, 3) + '****' + text.slice(-4);
}

/**
 * 邮箱掩码：ab***@example.com。
 * @param {string} value 邮箱
 * @returns {string} 掩码文本
 */
function maskEmail(value) {
    const text = String(value || '').trim();
    if (!text) {
        return '未绑定';
    }
    const at = text.indexOf('@');
    if (at <= 0) {
        return text;
    }
    return text.slice(0, Math.min(2, at)) + '***' + text.slice(at);
}

/**
 * 时间戳或日期字符串转本地可读文本。
 * @param {string|number} value 时间
 * @returns {string} 文本
 */
function formatTime(value) {
    if (value === null || value === undefined || value === '') {
        return '—';
    }
    const date = typeof value === 'number' ? new Date(value) : new Date(String(value).replace(/-/g, '/'));
    if (Number.isNaN(date.getTime())) {
        return String(value);
    }
    const pad = n => String(n).padStart(2, '0');
    return date.getFullYear() + '-' + pad(date.getMonth() + 1) + '-' + pad(date.getDate())
        + ' ' + pad(date.getHours()) + ':' + pad(date.getMinutes());
}

/**
 * 切换按钮忙碌态并保留原文案。
 * @param {HTMLButtonElement} button 按钮
 * @param {boolean} busy 是否忙碌
 * @param {string} label 忙碌时文案
 */
function setBusy(button, busy, label) {
    if (!button) {
        return;
    }
    if (busy) {
        if (!button.dataset.label) {
            button.dataset.label = button.textContent;
        }
        button.disabled = true;
        button.textContent = label;
    } else {
        button.disabled = false;
        if (button.dataset.label) {
            button.textContent = button.dataset.label;
            delete button.dataset.label;
        }
    }
}

/**
 * 在字段下方显示错误。
 * @param {HTMLElement} input 输入控件
 * @param {HTMLElement} errorEl 错误节点
 * @param {string} message 错误文案
 */
function showFieldError(input, errorEl, message) {
    const field = input.closest('.field');
    if (field) {
        field.classList.add('is-invalid');
    }
    input.setAttribute('aria-invalid', 'true');
    if (errorEl) {
        errorEl.textContent = message;
        errorEl.hidden = false;
    }
}

/**
 * 清除字段错误。
 * @param {HTMLElement} input 输入控件
 * @param {HTMLElement} errorEl 错误节点
 */
function clearFieldError(input, errorEl) {
    const field = input.closest('.field');
    if (field) {
        field.classList.remove('is-invalid');
    }
    input.removeAttribute('aria-invalid');
    if (errorEl) {
        errorEl.textContent = '';
        errorEl.hidden = true;
    }
}

/**
 * 在容器内展开二次确认，不弹模态。
 * @param {HTMLElement} container 容器
 * @param {{message: string, confirmLabel: string, onConfirm: Function, onCancel?: Function}} options
 */
function confirmInline(container, { message, confirmLabel, onConfirm, onCancel }) {
    const panel = document.createElement('div');
    panel.className = 'confirm';
    panel.innerHTML = '<p></p><button class="btn btn--secondary" type="button" data-cancel></button>'
        + '<button class="btn btn--primary" type="button" data-confirm></button>';
    $('p', panel).textContent = message;
    const cancelBtn = $('[data-cancel]', panel);
    const confirmBtn = $('[data-confirm]', panel);
    cancelBtn.textContent = '取消';
    confirmBtn.textContent = confirmLabel;

    const close = () => {
        panel.remove();
        if (typeof onCancel === 'function') {
            onCancel();
        }
    };
    cancelBtn.addEventListener('click', close);
    confirmBtn.addEventListener('click', () => {
        panel.remove();
        onConfirm(confirmBtn);
    });
    panel.addEventListener('keydown', event => {
        if (event.key === 'Escape') {
            close();
        }
    });

    container.appendChild(panel);
    confirmBtn.focus();
}

export { $, $$, toast, maskSecret, maskPhone, maskEmail, formatTime, setBusy, showFieldError, clearFieldError, confirmInline };
