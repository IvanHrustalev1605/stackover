const sidebarHTML = `
    <div id="left-sidebar" data-is-here-when="md lg" class="left-sidebar js-pinned-left-sidebar ps-relative">
        <div class="left-sidebar--sticky-container js-sticky-leftnav">
            <nav role="navigation">
                <ol class="nav-links">
                    <li class="ps-relative  youarehere" aria-current="true">
                        <a href="/" class="pl8 js-gps-track nav-links--link"
                           data-gps-track="top_nav.click({is_current: true, location:1, destination:8})" aria-controls=""
                           data-controller="" data-s-popover-placement="right" aria-current="page"
                           data-s-popover-auto-show="true" data-s-popover-hide-on-outside-click="never">
                            <div class="d-flex ai-center">
                                <div class="flex--item truncate">
                                    Главная
                                </div>
                            </div>
                        </a>
                    </li>
    
                    <li>
                        <ol class="nav-links">
                            <li class="fs-fine tt-uppercase ml8 mt16 mb4 fc-light">Публичные</li>
    
                            <li class="ps-relative" aria-current="false">
                                <a id="nav-questions" href="/questions"
                                   class="pl8 js-gps-track nav-links--link -link__with-icon"
                                   data-gps-track="top_nav.click({is_current: false, location:1, destination:1})"
                                   aria-controls="" data-controller="" data-s-popover-placement="right" aria-current="false"
                                   data-s-popover-auto-show="true" data-s-popover-hide-on-outside-click="never">
                                    <svg aria-hidden="true" class="svg-icon iconGlobe" width="18" height="18"
                                         viewBox="0 0 18 18">
                                        <path d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8ZM8 15.32a6.46 6.46 0 0 1-4.3-2.74 6.46 6.46 0 0 1-.93-5.01L7 11.68v.8c0 .88.12 1.32 1 1.32v1.52Zm5.72-2c-.2-.66-1-1.32-1.72-1.32h-1v-2c0-.44-.56-1-1-1H6V7h1c.44 0 1-.56 1-1V5h2c.88 0 1.4-.72 1.4-1.6v-.33a6.45 6.45 0 0 1 3.83 4.51 6.45 6.45 0 0 1-1.51 5.73v.01Z"></path>
                                    </svg>
                                    <span class="-link--channel-name">Вопросы</span>
                                </a>
                            </li>
    
                            <li class="ps-relative" aria-current="false">
                                <a id="nav-tags" href="/tags" class=" js-gps-track nav-links--link"
                                   data-gps-track="top_nav.click({is_current: false, location:1, destination:2})"
                                   aria-controls="" data-controller="" data-s-popover-placement="right" aria-current="false"
                                   data-s-popover-auto-show="true" data-s-popover-hide-on-outside-click="never">
                                    <div class="d-flex ai-center">
                                        <div class="flex--item truncate">
                                            Метки
                                        </div>
                                    </div>
                                </a>
                            </li>
    
                            <li class="ps-relative" aria-current="false">
                                <a id="nav-users" href="/users" class=" js-gps-track nav-links--link"
                                   data-gps-track="top_nav.click({is_current: false, location:1, destination:3})"
                                   aria-controls="" data-controller="" data-s-popover-placement="right" aria-current="false"
                                   data-s-popover-auto-show="true" data-s-popover-hide-on-outside-click="never">
                                    <div class="d-flex ai-center">
                                        <div class="flex--item truncate">
                                            Участники
                                        </div>
                                    </div>
                                </a>
                            </li>
    
                            <li class="ps-relative" aria-current="false">
                                <a id="nav-unanswered" href="/unanswered" class=" js-gps-track nav-links--link"
                                   data-gps-track="top_nav.click({is_current: false, location:1, destination:5})"
                                   aria-controls="" data-controller="" data-s-popover-placement="right" aria-current="false"
                                   data-s-popover-auto-show="true" data-s-popover-hide-on-outside-click="never">
                                    <div class="d-flex ai-center">
                                        <div class="flex--item truncate">
                                            Неотвеченные
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </ol>
                    </li>
    
                    <li>
                        <ol class="nav-links">
                        </ol>
                    </li>
                </ol>
            </nav>
        </div>
    </div>
`;

document.querySelector('.container').insertAdjacentHTML("afterbegin", sidebarHTML);