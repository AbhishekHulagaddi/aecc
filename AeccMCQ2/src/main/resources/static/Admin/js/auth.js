
async function login() {
    const userName = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    try {
        console.debug(window.BASE_URL);
        const response = await fetch(window.BASE_URL + "/Auth/User/signin", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userName, password })
        });

        if (!response.ok) throw new Error("Login failed");

        const data = await response.json();
        alert("User Logged In Successfully!!");

        // Store all values in session storage
        sessionStorage.setItem("userData", JSON.stringify(data));
        sessionStorage.setItem("token", data.token);
        sessionStorage.setItem("userId", data.userId);
        sessionStorage.setItem("userName", data.userName);
        sessionStorage.setItem("mail", data.mail);
        sessionStorage.setItem("roles", JSON.stringify(data.roles));
        sessionStorage.setItem("tokenExpiry", data.tokenExpiry);

        // Redirect to dashboard
        window.location.href = "managerdashboard.html";
    } catch (error) {
        document.getElementById('login-message').innerText = "Invalid credentials!";
    }
}
function logout() {
    localStorage.removeItem("isAdmin");
    window.location.href = "index.html";
}
