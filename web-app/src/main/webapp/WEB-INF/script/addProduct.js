const fileWindow = document.getElementById("file-window")
document.getElementById("plus-button").addEventListener('click',function (){
    const newFileInput = document.createElement('input');
    newFileInput.style.paddingTop="5px"
    newFileInput.type = "file";
    newFileInput.accept = "image/jpeg, image/png";
    newFileInput.name = "files"
    newFileInput.id="file"+ Number(fileWindow.children.length+1);
    newFileInput.setAttribute('multiple','')
    console.log("add element "+newFileInput.name,)
    fileWindow.appendChild(newFileInput);
    console.log("length "+ fileWindow.children.length)
})
document.getElementById("minus-button").addEventListener('click',function (){
    fileWindow.removeChild(fileWindow.lastElementChild)

})
