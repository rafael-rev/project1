// 2. when page loads, check for session
window.onload = async () => {
    let response = await fetch("http://localhost:8999/session");
    let responseBody = await response.json();
    console.log(responseBody) 
    if(responseBody.success){
        if(responseBody.data.role_id == 1){
            window.location = "./user"
        }else if(responseBody.data.role_id == 2){
            window.location = "./manager"
        }  
    }
}

// LOGIN FORM EVENT HANDLING
// 3. Grab login form element to var
let loginFormElem = document.getElementById("login-form");
// 4. Turn off default submit event behavior
loginFormElem.addEventListener("submit", (event) => {
    event.preventDefault();
    // 5. Store values from input, then call loginRequest function on those input vars
    let usernameInput = document.getElementById("username-input");
    let passwordInput = document.getElementById("password-input");
    loginRequest(usernameInput.value, passwordInput.value);
});

// 6. loginRequest function
async function loginRequest(username, password){
    let response = await fetch("http://localhost:8999/session", {
        method: "POST",
        body: JSON.stringify({
            "username": username,
            "password": password
        })
    });
    let responseBody = await response.json();
    // CHECK responseBody
    if(responseBody.success){
        if(responseBody.data.role_id == 1){
            console.log("Is an employee")
            window.location = "./user"
        }else if(responseBody.data.role_id == 2){
            console.log("Is a manager")
            window.location = "./manager"
        }  
    }else{
        let messageElem = document.getElementById("message");
        messageElem.style.display = "block";
        messageElem.innerText = responseBody.message;
    }
}