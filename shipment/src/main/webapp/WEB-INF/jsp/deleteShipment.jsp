<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/formPage.css">
    <title>Delete Shipments</title>
</head>

<%@ include file="common.jsp" %>

    <body>
        <h2>DELETE SHIPMENTS</h2>
        <form action="/api/shipments/trackingNumber" method="POST">
            <input type="hidden" name="_method" value="DELETE">
            <div class="form-container">
                <label for="trackingNumber">Tracking Number:</label>
                <input type="text" id="trackingNumber" name="trackingNumber" required>
                <br>
            </div>
            <div class="button-row">
                <input type="submit" value="SUBMIT" />
                <input type="reset" value="RESET" />
            </div>
        </form>
    </body>

</html>