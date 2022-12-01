console.log("header.js is ready!");

const header = document.createElement("header");
header.setAttribute("id", "header");

header.innerHTML = "<!--Header-->\n<div class=\"container-fluid\">\n<div class=\"row bg-light shadow\">\n        " +
                    "<div class=\"col-md-3\"></div>\n        " +
                    "<div class=\"col-md-6 d-flex my-3\">\n            " +
                    "<input class=\"form-control me-2\" type=\"search\" placeholder=\"Поиск\" aria-label=\"Search\">\n        " +
                    "</div>\n        " +
                    "<div class=\"col-md-3 my-3\">\n            " +
                    "<button class=\"btn btn-outline-primary rounded-1\" type=\"submit\">Войти</button>\n            " +
                    "<button class=\"btn btn-primary rounded-1\" type=\"submit\">Регистрация</button>\n        " +
                    "</div>\n    </div>\n</div>\n" +
                    "<!--End Header-->";

document.querySelector("body").prepend(header);

console.log("header is done!");