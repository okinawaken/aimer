/**
 * 阿里鱼页面请求层：token 解析、fetch 封装与错误映射。
 * token 依次取父窗口 getAppToken()、sessionStorage、URL 的 _token；取到后立即从地址栏移除。
 */

const TOKEN = (() => {
    const fromUrl = new URLSearchParams(location.search).get('_token') || '';
    if (fromUrl) {
        saveToken(fromUrl);
        const url = new URL(location.href);
        url.searchParams.delete('_token');
        history.replaceState(null, '', url.pathname + url.search + url.hash);
    }
    try {
        if (parent && parent.getAppToken) {
            const token = parent.getAppToken();
            if (token && token.token) {
                saveToken(token.token);
                return token.token;
            }
        }
    } catch (e) {
        // 页面不在 iframe 内时忽略
    }
    return readToken();
})();

const BASE = document.baseURI;

function saveToken(token) {
    try {
        sessionStorage.setItem('aliyu_token', token);
    } catch (e) {
        // 隐私模式或禁用存储时忽略
    }
}

function readToken() {
    try {
        return sessionStorage.getItem('aliyu_token') || '';
    } catch (e) {
        return '';
    }
}

/**
 * 请求阿里鱼接口。
 * @param {string} path 相对 /erupt-api/aliyu 的路径
 * @param {{method?: string, query?: object, body?: object, signal?: AbortSignal}} options
 * @returns {Promise<any>} R.data
 */
async function api(path, { method = 'GET', query, body, signal } = {}) {
    const url = new URL('erupt-api/aliyu/' + String(path).replace(/^\//, ''), BASE);
    if (query) {
        for (const [key, value] of Object.entries(query)) {
            if (value !== '' && value !== null && value !== undefined) {
                url.searchParams.set(key, value);
            }
        }
    }
    const headers = { token: TOKEN };
    const init = { method, headers, signal };
    if (body !== undefined) {
        headers['Content-Type'] = 'application/json';
        init.body = JSON.stringify(body);
    }

    let response;
    try {
        response = await fetch(url, init);
    } catch (e) {
        if (e && e.name === 'AbortError') {
            throw e;
        }
        throw new Error('网络请求失败，检查连接后重试');
    }

    if (response.status === 401) {
        try {
            parent.location.reload();
        } catch (e) {
            location.reload();
        }
        throw new Error('登录已失效');
    }
    if (response.status === 403) {
        throw new Error('无权限：请为该菜单授权');
    }

    const json = await response.json().catch(() => null);
    if (response.status >= 500) {
        throw new Error((json && (json.message || json.error)) || ('服务异常 ' + response.status));
    }
    if (json && json.success === false) {
        throw new Error(json.message || '操作失败');
    }
    return json ? json.data : null;
}

/**
 * 构造站内 tpl 页面地址。tpl 路由由服务端按 URL 参数校验登录态，站内跳转必须带上 _token。
 * @param {string} path 相对 /erupt-api/tpl/aliyu 的路径
 * @param {object} [query] 附加查询参数
 * @returns {string} 根路径相对地址
 */
function tplUrl(path, query = {}) {
    const url = new URL('erupt-api/tpl/aliyu/' + String(path).replace(/^\/+/, ''), BASE);
    url.searchParams.set('_token', TOKEN);
    for (const [key, value] of Object.entries(query)) {
        if (value !== '' && value !== null && value !== undefined) {
            url.searchParams.set(key, value);
        }
    }
    return url.pathname + url.search;
}

/**
 * 给页内指向其它 tpl 页面的静态链接补上 _token。
 * @param {ParentNode} [root] 查找范围
 */
function bindTplLinks(root = document) {
    root.querySelectorAll('a[href]').forEach(link => {
        const href = link.getAttribute('href');
        if (!href || !href.startsWith('erupt-api/tpl/aliyu/') || href.includes('_token=')) {
            return;
        }
        const url = new URL(href, BASE);
        url.searchParams.set('_token', TOKEN);
        link.setAttribute('href', url.pathname + url.search);
    });
}

bindTplLinks();

export { TOKEN, api, tplUrl, bindTplLinks };
