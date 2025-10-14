const hiddenDesc = document.querySelector('.hidden-desc');
const pibBtn = document.querySelector('.pib-name');
const closeBtn = document.querySelector('.close');

pibBtn.addEventListener('click', (e) => {
    e.preventDefault();

    hiddenDesc.style.display = 'block';
});

closeBtn.addEventListener('click', (e) => {
    e.preventDefault();

    hiddenDesc.style.display = 'none';
});

window.addEventListener('click', (e) => {
    e.preventDefault();
    
    if (e.target == hiddenDesc) {
        hiddenDesc.style.display = 'none';
    }
});