const modelHeader = [
    {type: 'header', value: [
            'Мой профиль'
        ]}
]

const $header = document.querySelector("#header")

modelHeader.forEach(block => {
    let html = ''

    if (block.type === 'header') {
        html = header(block)
    }

    $header.insertAdjacentHTML('beforeend', html)
})

function header(block) {
    return `
<header class="s-topbar ps-fixed t0 l0 js-top-bar">
    <div class="s-topbar--container">

        <a href="/index" class="s-topbar--logo">
            <span class="-img _glyph">Stack Overflow</span>
        </a>


        <form id="search" role="search" action=/search class="s-topbar--searchbar js-searchbar " autocomplete="off">
            <div class="s-topbar--searchbar--input-group">
                <input name="q"
                       type="text"
                       placeholder="Поиск..."
                       value=""
                       autocomplete="off"
                       maxlength="240"
                       class="s-input s-input__search js-search-field "
                       aria-label="Поиск"
                       aria-controls="top-search"
                       data-controller="s-popover"
                       data-action="focus->s-popover#show"
                       data-s-popover-placement="bottom-start"/>
                <svg aria-hidden="true" class="s-input-icon s-input-icon__search svg-icon iconSearch" width="18"
                     height="18" viewBox="0 0 18 18">
                    <path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"/>
                </svg>
                <div class="s-popover p0 wmx100 wmn4 sm:wmn-initial js-top-search-popover" id="top-search" role="menu">
                    <div class="s-popover--arrow"></div>
                    <div class="js-spinner p24 d-flex ai-center jc-center d-none">
                        <div class="s-spinner s-spinner__sm fc-orange-400">
                            <div class="v-visible-sr">Loading&#x2026;</div>
                        </div>
                    </div>

                    <span class="v-visible-sr js-screen-reader-info"></span>
                    <div class="js-ac-results overflow-y-auto hmx3 d-none"></div>

                    <div class="js-search-hints" aria-describedby="Tips for searching"></div>
                </div>
            </div>
        </form>

        <nav class="h7 ml-auto overflow-x-auto pr12">
            <a type="button" class="btn btn-info" href="/index">Мой профиль</a>
        </nav>
    </div>
</header>
    `
}
