const modelFooter = [
    {type: 'footer', value: [
            'Stack Overflow',
            'Справка'
        ]}
]

const $footer = document.querySelector("#footer")

modelFooter.forEach(block => {
    let html = ''

    if (block.type === 'footer') {
        html = footer(block)
    }

    $footer.insertAdjacentHTML('beforeend', html)
})

function footer(block) {
    return `
<footer class="site-footer js-footer " role="contentinfo">
    <div class="site-footer--container">
        <nav class="site-footer--nav">
            <div class="site-footer--col">
                <h5 class="-title"><a href="/index">Stack Overflow</a>
                </h5>
                <ul class="-list js-primary-footer-links">
                    <li><a href="/help" class="-link">Справка</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</footer>
    `
}