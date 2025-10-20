const logout = document.querySelector("#logout");

document.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch("/api/user/info");
        if (!response.ok) throw new Error("Request Failed!");

        const data = await response.json();

        // update username and email
        document.querySelector("#username").textContent = `Username: ${data.name}`;
        document.querySelector("#email").textContent = `Email: ${data.email}`;

        document.querySelectorAll(".unauthenticated").forEach(el => el.style.display = "none");
        document.querySelectorAll(".authenticated").forEach(el => {
            el.style.display = "flex",
            el.style.flexDirection = "column",
            el.style.justifyContent = "center",
            el.style.alignItems = "flex-end",
            el.style.width = "95%",
            el.style.padding = "8px",
            el.style.position = "absolute",
            el.style.top = "1%"
        });
        const dogsList = document.querySelector("#dogs-list");
        dogsList.style.display = "block";
        dogsList.style.fontSize = "20px";
        dogsList.style.textDecoration = "none";
    } catch (error) {
        console.error("Error fetching user: ", error)
    }
});

logout.addEventListener("click", async () => {
    try {
        const response = await fetch("/logout", {
            method: "POST",
            credentials: "include"
        });

        if (!response.ok) throw new Error("Request Failed!");

        // clear username and email
        document.querySelector("#username").textContent = "";
        document.querySelector("#email").textContent = "";

        // hide .authenticated elements
        document.querySelectorAll(".authenticated").forEach(el => el.style.display = "none");
    } catch (error) {
        console.error("Error logging user out: ", error)
    }
});