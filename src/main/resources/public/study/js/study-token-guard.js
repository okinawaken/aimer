/* ==========================================================================
 * 自习室 · Token 绑定守卫
 * 在加载任何上游数据前，先校验当前用户是否已绑定小程序 token；
 * 未绑定时渲染引导卡片，跳转至「用户设置」完成绑定。
 * 各 tpl 页面在初始化前调用 StudyTokenGuard.ensure(onBound)。
 * ========================================================================== */
(function () {
    'use strict';

    var TOKEN = (function () {
        try {
            if (parent && parent.getAppToken) {
                var t = parent.getAppToken();
                if (t && t.token) return t.token;
            }
        } catch (e) {
        }
        return new URLSearchParams(location.search).get('_token') || '';
    })();
    var BASE = document.baseURI;

    function userSettingUrl() {
        var u = new URL('erupt-api/tpl/study/user-setting', BASE);
        u.searchParams.set('_token', TOKEN);
        return u.href;
    }

    function fetchSetting() {
        var url = new URL('erupt-api/study/user-setting/me', BASE);
        return fetch(url, {headers: {token: TOKEN}}).then(function (res) {
            if (res.status === 401) {
                try {
                    parent.location.reload();
                } catch (e) {
                    location.reload();
                }
                throw new Error('登录已失效');
            }
            return res.json().catch(function () {
                return null;
            });
        }).then(function (json) {
            return (json && json.success) ? json.data : null;
        });
    }

    function renderGuide() {
        var mask = document.createElement('div');
        mask.className = 'token-guard';
        var goUrl = userSettingUrl();
        mask.innerHTML =
            '<div class="token-guard-card">' +
            '<div class="tg-icon" aria-hidden="true">未绑定</div>' +
            '<h2>尚未绑定小程序 Token</h2>' +
            '<p>使用自习室功能前，需要先在「用户设置」中绑定微信小程序登录凭据（Token），绑定完成后回到本页面刷新即可。</p>' +
            '<a class="btn primary" href="' + goUrl + '">前往用户设置</a>' +
            '</div>';
        document.body.innerHTML = '';
        document.body.appendChild(mask);
    }

    window.StudyTokenGuard = {
        TOKEN: TOKEN,
        BASE: BASE,
        /**
         * 校验 token 绑定状态：已绑定则执行 onBound，未绑定则渲染引导卡片。
         * @param {Function} onBound 已绑定时的回调
         */
        ensure: function (onBound) {
            return fetchSetting().then(function (data) {
                var cfg = (data && data.config) || {};
                var token = cfg.token ? String(cfg.token).trim() : '';
                if (token) {
                    if (typeof onBound === 'function') onBound();
                    return;
                }
                renderGuide();
            }).catch(function () {
                /* 读取设置失败时不阻断页面，交由各接口自身的错误处理 */
                if (typeof onBound === 'function') onBound();
            });
        }
    };
})();
