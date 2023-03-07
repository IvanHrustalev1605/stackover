const header = document.createElement("header");

header.innerHTML = `
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
<div class="container">
    <ul class="navbar-nav mr-auto" id="navbar-hamburger">
              <li class="nav-item dropdown">
        <a class="nav-link mr-3" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <img src="/images/hamburger_icon_white.svg" width="25px" alt="hamburger">
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="\questions">Вопросы</a>
          <a class="dropdown-item" href="\tags">Метки</a>
          <a class="dropdown-item" href="\/users">Пользователи</a>
        </div>
      </li>
    </ul>
  <a class="navbar-brand mr-4" href="/">
    <img src="/images/logo.svg" width="120" alt="logo">
  </a>


  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item mr-3">
        <a class="nav-link" href="/about">О нас</a>
      </li>
      <li class="nav-item mr-3">
        <a class="nav-link" href="/products">Продукты</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/forteams">Для команд</a>
      </li>

    </ul>
    <form class="row" action="/search" method="post">
      <div class="col mr-2">
          <input class="form-control" type="search" placeholder="Поиск">                
      </div>
    </form>
    <a href="/login" class="btn btn-light mr-2">Войти</a>
    <a href="/register" class="btn btn-primary">Регистрация</a>
  </div>
</nav>
`
document.body.insertBefore(header, document.body.firstChild);
document.body.style.paddingTop = "40px";

if (window.location.pathname === '/questions' ||
    window.location.pathname === '/tags' ||
    window.location.pathname === '/users') {
    document.getElementById('navbar-hamburger').style.display = 'none';
}
