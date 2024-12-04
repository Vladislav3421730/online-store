const fileWindow = document.getElementById("file-window")
document.getElementById("plus-button").addEventListener('click',function (){
    const newFileInput = document.createElement('input');
    newFileInput.style.paddingTop="5px"
    newFileInput.type = "file";
    newFileInput.accept = "image/jpeg, image/png";
    newFileInput.name = "file"+ fileWindow.children.length;
    newFileInput.id="file"+ fileWindow.children.length;
    console.log("add element "+newFileInput.name,)
    fileWindow.appendChild(newFileInput);
    console.log("length "+ fileWindow.children.length)
})
document.getElementById("minus-button").addEventListener('click',function (){
    fileWindow.removeChild(fileWindow.lastElementChild)

})
