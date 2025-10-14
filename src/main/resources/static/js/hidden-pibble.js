const hiddenDescs = document.querySelectorAll('.hidden-desc');
const pibBtns = document.querySelectorAll('.pib-name');
const closeBtn = document.querySelectorAll('.close');

pibBtns.forEach((btn, index) => {
    const modal = hiddenDescs[index];
    const close = closeBtn[index];

    btn.addEventListener('click', () => {
        modal.style.display = 'block';
    });

    close.addEventListener('click', () => {
        modal.style.display = 'none';
    });
    
    window.addEventListener('click', (e) => {        
        if (e.target == modal) {
            modal.style.display = 'none';
        }
    });
});

