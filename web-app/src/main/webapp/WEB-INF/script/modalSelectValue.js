const options = document.querySelectorAll('#category option');
options.forEach(option => {
    if (option.hasAttribute('selected')) {
        option.value = '';
    } else {
        option.value = option.textContent;
    }
});