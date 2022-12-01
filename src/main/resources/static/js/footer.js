console.log("footer.js is ready!");

const footer = document.createElement('footer');

footer.setAttribute("id", "footer");
footer.setAttribute("class", "py-md-5 bg-dark text-white fixed-bottom");

footer.innerHTML = "<div class=\"row mx-auto\"></div>";

document.querySelector("body").appendChild(footer);

console.log("footer is done!")