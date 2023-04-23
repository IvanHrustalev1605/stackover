document.body.innerHTML = `
        <header class="position-relative" style="margin:0 10% -17px 10%;width: auto;
                                           border-bottom:1px solid rgba(178,171,171,0.46);padding-bottom: 10px">
    <div id="header-nav" class="d-flex justify-content-around align-items-center flex-wrap ">
      <div id="header-img" class="">
      <img src="/static/images/kata-academy-logo.jpg" alt="" width="120" height="40">
      </div>
        <div id="header-search" class="d-flex align-items-center justify-content-center flex-column gap-4">
          <form class="d-flex" style="flex-shrink: 1">
            <input class="form-control me-1" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-primary btn-sm" type="submit">Search</button>
          </form>
        </div>
       
      <div id="header-information">
      <a href="#" style="text-decoration: none">
          <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-question-square-fill" viewBox="0 0 16 16">
            <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm3.496 6.033a.237.237 0 0 1-.24-.247C5.35 4.091 6.737 3.5 8.005 3.5c1.396 0 2.672.73 2.672 2.24 0 1.08-.635 1.594-1.244 2.057-.737.559-1.01.768-1.01 1.486v.105a.25.25 0 0 1-.25.25h-.81a.25.25 0 0 1-.25-.246l-.004-.217c-.038-.927.495-1.498 1.168-1.987.59-.444.965-.736.965-1.371 0-.825-.628-1.168-1.314-1.168-.803 0-1.253.478-1.342 1.134-.018.137-.128.25-.266.25h-.825zm2.325 6.443c-.584 0-1.009-.394-1.009-.927 0-.552.425-.94 1.01-.94.609 0 1.028.388 1.028.94 0 .533-.42.927-1.029.927z"/>
          </svg>
        </a>
      </div>
      <div id="header-login" class="p-1">
        <button id="login-button" class="btn btn-primary btn-sm" type="submit">Login</button>
      </div>
      <div id="header-register" class="">
        <button id="register-button" class="btn btn-primary btn-sm" type="submit">Register</button>
      </div>
    </div>
  </header>
` + document.body.innerHTML;
let element = `<div id="main-body" class="row mt-3">
      <div id="left-side-bar" class="col">
        <div class="list-group">
          <a href="#" class="list-group-item">
            <span class="glyphicon glyphicon-star"></span> Главная
          </a>
          <a href="#" class="list-group-item">
            <span class="glyphicon glyphicon-user"></span> Все вопросы
          </a>
          <a href="#" class="list-group-item active">
            <span class="glyphicon glyphicon-user"></span> Метки
          </a>
          <a href="#" class="list-group-item">
            <span class="glyphicon glyphicon-user"></span> Участники
          </a>
          <a href="#" class="list-group-item">
            <span class="glyphicon glyphicon-user"></span> Неотвеченные вопросы
          </a>
        </div>
      </div>

    </div>`
document.getElementById('left-side-bar').innerHTML = element
let footer = `
      <!-- Footer -->
        <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
          © 2023 Copyright:
          <a class="text-reset fw-bold" href="https://kata.academy/">KATA_ACADEMY</a>
        </div>

`;
document.getElementById('footer').innerHTML = footer