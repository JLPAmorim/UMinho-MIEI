function multiForm(){

    var desc = $(`
        <div class="w3-row w3-margin-bottom" id="addedFields">
            <div class="w3-col s3">
                <label class="w3-text-teal">Description</label>
            </div>
            <div class="w3-col s9 w3-border">
                <input class="w3-input w3-border w3-light-grey" type="text" name="desc" required>
            </div>
        </div>
    `)
    
    var img = $(`
        <div class="w3-row w3-margin-bottom" id="addedFields">
            <div class="w3-col s3">
                <label class="w3-text-teal">Select file</label>
            </div>
            <div class="w3-col s9 w3-border">
                <input class="w3-input w3-border w3-light-grey" type="file" name="myFile" required>
            </div>
        </div>
    `)

    $("#addedFields").append(desc)
    $("#addedFields").append(img)
}