// This script was made by AI, I don't know Javascript

document.getElementById("loginForm").addEventListener("submit", (e) => {
    e.preventDefault(); // prevent normal form submission

    const name = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("/tokenGeneration", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name, email, password })
    })
    .then(response => {
        if (!response.ok) throw new Error("Login failed");
        return response.json();
    })
    .then(data => {
        const token = data.token;
        console.log("JWT token:", token);

        // Save token in localStorage or sessionStorage
        localStorage.setItem("jwtToken", token);

        // Redirect or fetch protected data
        window.location.href = "/home/auth"; // for example
    })
    .catch(error => {
        alert("Authentication failed: " + error.message);
    });

    fetch("/home/auth", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("jwtToken")
        }
    });
});
