let element = `<div id="main-body" class="row mt-3">
      <div id="left-side-bar" class="col">
        <div class="list-group">
          <a href="#" class="list-group-item">
            <span class="glyphicon glyphicon-star"></span> Главная
          </a>
          <a href="#" class="list-group-item">
            <span class="glyphicon glyphicon-user"></span> Все вопросы
          </a>
          <a href="#" class="list-group-item">
            <span class="glyphicon glyphicon-user"></span> Тэги
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