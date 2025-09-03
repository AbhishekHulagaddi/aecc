const API_URL = "http://localhost:8081/Auth";

async function requestOTP() {
  const email = document.getElementById('forgot-email').value;
  const messageBox = document.getElementById('forgot-message');

  if (!email) {
    messageBox.innerText = "Please enter your email.";
    return;
  }

  try {
    const response = await fetch(`${API_URL}/User/sendOtp`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ mail: email })
    });

    if (!response.ok) throw new Error("Failed to send OTP");

    const statusData = await response.json();

    messageBox.style.color = (statusData.status === 200) ? "green" : "red";
    messageBox.innerText = statusData.message || "Unexpected response from server.";
  } catch (error) {
    messageBox.style.color = "red";
    messageBox.innerText = "Error: " + error.message;
  }
}

async function resetPassword() {
  const email = document.getElementById('forgot-email').value;
  const otp = document.getElementById('otp').value;
  const newPassword = document.getElementById('new-password').value;
  const confirmPassword = document.getElementById('confirm-password').value;
  const messageBox = document.getElementById('forgot-message');

  if (!otp || !newPassword || !confirmPassword) {
    messageBox.innerText = "Please fill Mandatory fields.";
    return;
  }

  if (newPassword !== confirmPassword) {
    messageBox.innerText = "Passwords do not match.";
    return;
  }

  try {
    const response = await fetch(`${API_URL}/User/ForgotPassword`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ 
          newPassword:newPassword,
          confirmPassword:confirmPassword,
          mail:email, 
          otp:otp })
    });

    if (!response.ok) throw new Error("Failed to reset password");
    const statusData = await response.json();

    messageBox.style.color = (statusData.status === 200) ? "green" : "red";
    messageBox.innerText = statusData.message || "Unexpected response from server.";
  } catch (error) {
    messageBox.style.color = "red";
    messageBox.innerText = "Error: " + error.message;
  }
}
