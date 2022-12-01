console.log("sidebar.js is ready!");
const sidebar = document.createElement("div");

sidebar.setAttribute("id", "left-sidebar");
sidebar.setAttribute("class", "col-2 flex-column border-end");

sidebar.innerHTML = "<ul class=\"nav flex-column\" style=\"padding: 15px; width: 164px;\">\n" +
                    "<li class=\"nav-item\">\n" +
                    "<a class=\"nav-link active\" aria-current=\"page\" href=\"#\">Главная</a>\n" +
                    "</li>\n" + "<li class=\"nav-item\">\n" + "<a class=\"nav-link\" href=\"#\">Публичные</a>\n" +
                    "</li>\n <li class=\"nav-item\">\n" +
                    "<a class=\"nav-link\" href=\"#\">Вопросы</a>\n " +
                    "</li>\n<li class=\"nav-item\">\n " +
                    "<a class=\"nav-link\" href=\"#\">Метки</a>\n</li>\n<li class=\"nav-item\">\n" +
                    "<a class=\"nav-link\" href=\"#\">Участники</a>\n</li>\n<li class=\"nav-item\">\n" +
                    "<a class=\"nav-link\" href=\"#\">Неотвеченные</a>\n </li>\n </ul>";

document.querySelector("div.row").prepend(sidebar);

console.log("sidebar is done!")
