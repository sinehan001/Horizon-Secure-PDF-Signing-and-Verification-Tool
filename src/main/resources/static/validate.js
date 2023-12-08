$(document).ready(function(){

    initFileUploader("#zdrop");
    function initFileUploader(target) {
        var previewNode = document.querySelector("#zdrop-template");
        previewNode.id = "";
        var previewTemplate = previewNode.parentNode.innerHTML;
        previewNode.parentNode.removeChild(previewNode);


        var zdrop = new Dropzone(target, {
            url: 'verify',
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
            popup();
            successMessage.innerText = response.message;
        });

        zdrop.on("error", function (file, errorMessage, xhr) {
            // Handle the error, for example, change the progress bar color to red
            var progr = file.previewElement.querySelector(".progress");
            if (progr) {
                progr.style.display = "none";
            }
            if(xhr.status === 400 || xhr.status === 500) {
                var error_Message = file.previewElement.querySelector(".dz-error-message");
                error_Message.innerText = errorMessage.error;
                error_popup();
            }
        });

    }

});

function error_popup() {
    var modal1 = document.querySelector(".modal-view1");
    var closeButton1 = document.querySelector(".close-button1");

    function toggleModal() {
        modal1.classList.toggle("show-modal");
    }

    function windowOnClick(event) {
        if (event.target === modal1) {
            toggleModal();
        }
    }
    closeButton1.addEventListener("click", toggleModal);
    window.addEventListener("click", windowOnClick);

    // Show the modal immediately when the function is called
    toggleModal();
}

function popup() {
    var modal = document.querySelector(".modal-view");
    var closeButton = document.querySelector(".close-button");

    function toggleModal() {
        modal.classList.toggle("show-modal");
    }

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