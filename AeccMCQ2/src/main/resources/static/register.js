//const API_URL = "http://localhost:8081/Auth/";
const API_URL = "http://tierraagniveshacoaching.up.railway.app/Auth/";


async function register() {
    const firstName = document.getElementById('register-firstname').value;
    const lastName = document.getElementById('register-lastname').value;
    const address = document.getElementById('register-address').value;
    const otp = document.getElementById('otp').value;
    const mail = document.getElementById('register-email').value;
    const mobileNumber = document.getElementById('register-mobile').value;
    const password = document.getElementById('register-password').value;
    const confirmpassword = document.getElementById('register-confirmpassword').value;
    const messageBox = document.getElementById('register-message');
    const userName = mail;
    const panNumber='';


    if (password !== confirmpassword) {
        messageBox.style.color = "red";
        messageBox.innerText = "Passwords do not match.";
        return;
    }

    showSpinner2();
    try {
    const response = await fetch(`${API_URL}User/Create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            userName,
            firstName,
            lastName,
            address,
            panNumber,
            otp,
            mail,
            mobileNumber,
            password,
            confirmpassword
        })
    });

    if (!response.ok) {
        throw new Error(`Server responded with status ${response.status}`);
    }

    const statusData = await response.json();

    messageBox.style.color = (statusData.status === 201) ? "green" : "red";
    messageBox.innerText = statusData.message || "Unexpected response from server.";
} catch (error) {
    messageBox.style.color = "red";
    messageBox.innerText = "Error: " + error.message;
}finally{
    hideSpinner2();
}

}


async function requestOTP() {
  const email = document.getElementById('register-email').value;
  const messageBox = document.getElementById('register-message');

  if (!email) {
    messageBox.innerText = "Please enter your email.";
    return;
  }

  showSpinner1();

  try {
    const response = await fetch(`${API_URL}/User/sendOtp`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ mail: email,userType:'New'
       })
    });

    if (!response.ok) throw new Error("Failed to send OTP");

    const statusData = await response.json();

    messageBox.style.color = (statusData.status === 200) ? "green" : "red";
    messageBox.innerText = statusData.message || "Unexpected response from server.";
  } catch (error) {
    messageBox.style.color = "red";
    messageBox.innerText = "Error: " + error.message;
  } finally{
    hideSpinner1();
  }
}


function showSpinner1() {
    document.getElementById('spinner1').style.display = 'block';
}

function hideSpinner1() {
    document.getElementById('spinner1').style.display = 'none';
}

function showSpinner2() {
    document.getElementById('spinner2').style.display = 'block';
}

function hideSpinner2() {
    document.getElementById('spinner2').style.display = 'none';
}
