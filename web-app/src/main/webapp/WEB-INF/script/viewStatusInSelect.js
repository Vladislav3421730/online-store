const select = document.getElementById("select")
const statusValue = String("${requestScope.orderDto.getStatus()}")
const options = Array.from(select.options);
options.forEach(option => {
    if (option.value === statusValue) {
        option.setAttribute('selected', '')
    }
})