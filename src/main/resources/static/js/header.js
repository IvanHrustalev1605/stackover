const headerHTML = `
    <header class="s-topbar ps-fixed t0 l0 js-top-bar">
        <div class="s-topbar--container">
            <a href="/" class="s-topbar--menu-btn js-left-sidebar-toggle" role="menuitem" aria-haspopup="true"
               aria-controls="left-sidebar" aria-expanded="false"><span></span></a>
            <div class="topbar-dialog leftnav-dialog js-leftnav-dialog dno">
                <div class="left-sidebar js-unpinned-left-sidebar" data-can-be="left-sidebar" data-is-here-when="sm"></div>
            </div>
            <a href="/" class="s-topbar--logo js-gps-track"
               data-gps-track="top_nav.click({is_current:true, location:1, destination:8})">
                <span class="-img _glyph">Stack Overflow</span>
            </a>
    
            <form id="search" role="search" action="/search" class="s-topbar--searchbar js-searchbar " autocomplete="off">
                <div class="s-topbar--searchbar--input-group">
                    <input name="q" type="text" role="combobox" placeholder="Поиск..." value="" autocomplete="off"
                           maxlength="240" class="s-input s-input__search js-search-field " aria-label="Поиск"
                           aria-controls="top-search" data-controller="s-popover" data-action="focus->s-popover#show"
                           data-s-popover-placement="bottom-start" aria-expanded="false">
                    <svg aria-hidden="true" class="s-input-icon s-input-icon__search svg-icon iconSearch" width="18"
                         height="18" viewBox="0 0 18 18">
                        <path d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"></path>
                    </svg>
                    <div class="s-popover p0 wmx100 wmn4 sm:wmn-initial js-top-search-popover" id="top-search" role="menu">
                        <div class="s-popover--arrow"></div>
                        <div class="js-spinner p24 d-flex ai-center jc-center d-none">
                            <div class="s-spinner s-spinner__sm fc-orange-400">
                                <div class="v-visible-sr">Loading...</div>
                            </div>
                        </div>
    
                        <span class="v-visible-sr js-screen-reader-info"></span>
                        <div class="js-ac-results overflow-y-auto hmx3 d-none"></div>
    
                        <div class="js-search-hints" aria-describedby="Tips for searching"></div>
                    </div>
                </div>
            </form>
    
            <nav class="h100 ml-auto overflow-x-auto pr12">
                <ol class="s-topbar--content" role="menubar">
                    <li role="none">
                        <a href="/profile"
                           class="s-topbar--item s-topbar--item__unset s-btn s-btn__filled ws-nowrap js-gps-track"
                           role="menuitem" rel="nofollow">Мой профиль</a>
                    </li>
                </ol>
            </nav>
        </div>
    </header>
`;

const headHTML = `
    <link rel="stylesheet" type="text/css" href="https://cdn.sstatic.net/Shared/stacks.css?v=cb9621e41d1f">
    <link rel="stylesheet" type="text/css" href="https://cdn.sstatic.net/Sites/ru/primary.css?v=958fa35fb644">
`;

document.body.setAttribute('class', 'home-page unified-theme');
document.body.insertAdjacentHTML("afterbegin", headerHTML);
document.head.insertAdjacentHTML("beforeend", headHTML);