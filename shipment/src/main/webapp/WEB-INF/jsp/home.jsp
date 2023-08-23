<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../css/home.css">
    <title>Home</title>
</head>

<%@ include file="common.jsp" %>

    <body>
        <h2>HOME</h2>
        <div class="row">
            <a href="addShipment">
                <button>ADD SHIPMENTS</button>
            </a>
            <a href="updateShipment">
                <button>UPDATE SHIPMENTS</button>
            </a>
        </div>
        <div class="row">
            <a href="viewShipment">
                <button>VIEW SHIPMENTS</button>
            </a>
            <a href="deleteShipment">
                <button>DELETE SHIPMENTS</button>
            </a>
        </div>
    </body>

</html>