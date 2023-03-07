function sidebarInput() {
    let bodyHTML = document.body.innerHTML;
    document.body.innerHTML =
        `<div class="container">
<div class="row">
    <div class="col-1 position-fixed" style="padding-top: 80px">
        <ul class="navbar-nav mr-auto">

            <li class="nav-item mr-3">
                <a class="nav-link text-dark" href="/">На главную</a>
            </li>
            <p></p>
            <li class="nav-item">
                <a class="nav-link text-dark" href="/questions">Вопросы</a>
            </li>
            <li class="nav-item">
                <a class="nav-link  text-dark" href="/tags">Метки</a>
            </li>
            <li class="nav-item">
                <a class="nav-link  text-dark" href="/users">Пользователи</a>
            </li>

        </ul>
    </div>
    <div class="col offset-2  border-left">` + bodyHTML;


    document.body.innerHTML +=
        `
            </div>
        </div>
    </div>`;
}

if (window.location.pathname === '/questions' ||
    window.location.pathname === '/tags' ||
    window.location.pathname === '/users') {
    sidebarInput();
}