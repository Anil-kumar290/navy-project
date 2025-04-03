document.addEventListener("DOMContentLoaded", function () {
    console.log("DOM fully loaded. Attempting to fetch data from servlet...");

    fetch('/bin/mycustomservlet')
        .then(response => {
            console.log("HTTP Status Code:", response.status); // Logs 200 or error status

            if (!response.ok) {
                throw new Error("HTTP error! Status: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log("Response from Servlet:", data); // Logs the JSON response

            // Ensure element exists before updating it
            const messageElement = document.getElementById("servlet-message");
            if (messageElement) {
                messageElement.innerText = data.message;
            } else {
                console.warn("Element with ID 'servlet-message' not found!");
            }
        })
        .catch(error => console.error("Error fetching servlet data:", error));
});
