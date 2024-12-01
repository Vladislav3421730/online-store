const password=document.getElementById("register-password")
const passwordConfirm=document.getElementById("confirmPassword")
const message=document.getElementById("passwordNotTheSame")
const submitButton=document.getElementById("register-button")
passwordConfirm.addEventListener('change',function (){

  if(password.value!==passwordConfirm.value){
    message.textContent="Пароли должны быть одинаковыми"
    submitButton.setAttribute('disabled','')
  }
  else {
    message.textContent=''
    submitButton.removeAttribute('disabled')
  }
})