$(document).ready(function(){

    initFileUploader("#zdrop");
    function initFileUploader(target) {
        var previewNode = document.querySelector("#zdrop-template");
        previewNode.id = "";
        var previewTemplate = previewNode.parentNode.innerHTML;
        previewNode.parentNode.removeChild(previewNode);


        var zdrop = new Dropzone(target, {
            url: 'upload',
            maxFiles:1,
            maxFilesize:30,
            acceptedFiles: ".pdf", // Only accept PDF files
            addRemoveLinks: true, // Show remove links for uploaded files
            dictDefaultMessage: "Drop PDF files here or click to upload",
            dictRemoveFile: "Remove file",
            previewTemplate: previewTemplate,
            previewsContainer: "#previews",
            clickable: "#upload-label"
        });

        zdrop.on("addedfile", function(file) {
            $('.preview-container').css('visibility', 'visible');
        });

        zdrop.on("totaluploadprogress", function (progress) {
            var progr = document.querySelector(".progress .progress-bar .determinate");
            if (progr === undefined || progr === null)
                return;
            progr.style.width = progress + "%";
        });

        zdrop.on('dragenter', function () {
            $('.fileuploader').addClass("active");
        });

        zdrop.on('dragleave', function () {
            $('.fileuploader').removeClass("active");
        });

        zdrop.on('drop', function () {
            $('.fileuploader').removeClass("active");
        });

        zdrop.on('success', function(file, response) {
            // Handle the success event, for example, show a success message
            var successMessage = file.previewElement.querySelector(".dz-success-message");
            popup(response.url);
            document.querySelector(".download").disabled = false;
            successMessage.innerText = response.message;
        });

        zdrop.on("error", function (file, errorMessage, xhr) {
            // Handle the error, for example, change the progress bar color to red
            var progr = file.previewElement.querySelector(".progress");
            if (progr) {
                progr.style.display = "none";
            }
        });

    }

});

function popup(url) {
    var modal = document.querySelector(".modal-view");
    var download = document.querySelector(".download");
    var download_message = document.querySelector(".download-message");
    var countdown = document.getElementById("countdown");
    var closeButton = document.querySelector(".close-button");

    function toggleModal() {
        modal.classList.toggle("show-modal");
    }

    download.addEventListener('click', function() {
        window.location.href = url;
        // 'download/signedFile?filename=eb81de1b-0593-4455-8907-f34541f5ad05.pdf'
        // Disable the button
        download.disabled = true;
        download_message.style.display = 'block';
        let seconds = 10;
        var countdownInterval = setInterval(()=>{
            if(--seconds>0) {
                countdown.innerText = seconds;
            }
            else {
                seconds = 10;
                countdown.innerText = 10;
                download.disabled = false;
                download_message.style.display = 'none';
                clearInterval(countdownInterval);
            }
        },1000);
    });

    function windowOnClick(event) {
        if (event.target === modal) {
            toggleModal();
        }
    }
    closeButton.addEventListener("click", toggleModal);
    window.addEventListener("click", windowOnClick);

    // Show the modal immediately when the function is called
    toggleModal();
}