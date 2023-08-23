<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/formPage.css">
    <title>Add Shipments</title>
</head>

<%@ include file="common.jsp" %>

    <body>
        <h2>ADD SHIPMENTS</h2>
        <form action="/api/shipments" method="POST">
            <div class="form-container">
                <label for="origin">Origin:</label>
                <input type="text" id="origin" name="origin" required>
                <br>

                <label for="destination">Destination:</label>
                <input type="text" id="destination" name="destination" required>
                <br>

                <label for="shipmentDate">Shipment Date:</label>
                <input type="datetime-local" id="shipmentDate" name="shipmentDate" required>
                <br>
            </div>
            <div class="button-row">
                <input type="submit" value="SUBMIT">
                <input type="reset" value="RESET">
            </div>
        </form>
    </body>

</html>