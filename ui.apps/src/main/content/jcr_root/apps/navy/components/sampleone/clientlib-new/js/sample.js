document.addEventListener("DOMContentLoaded", function () {
    const name = "anil"; // Change this value dynamically if needed
    const servletUrl = `/bin/mycustomservlet?name=${encodeURIComponent(name)}`;

    console.log("Calling servlet with URL:", servletUrl); // Debugging log

    fetch(servletUrl)
        .then(response => {
            console.log("HTTP Status Code:", response.status); // Check response status
            if (!response.ok) {
                throw new Error("HTTP error! Status: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log("Response from Servlet:", data); // Debugging log

            const messageElement = document.getElementById("servlet-message");
            if (messageElement) {
                messageElement.innerText = data.message;
            } else {
                console.warn("Element with ID 'servlet-message' not found.");
            }
        })
        .catch(error => console.error("Error calling servlet:", error));
});
