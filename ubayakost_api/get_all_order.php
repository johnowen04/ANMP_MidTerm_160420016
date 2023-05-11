<?php
require_once('env.php');

$conn = new mysqli($_ENV['SERVER_NAME'], $_ENV['USER_NAME'], $_ENV['USER_PASSWORD'], $_ENV['DATABASE_NAME']) or die("Connection error: " . $conn->error);

$arr = [];

extract($_POST);

$sql = "SELECT o.properties_id, o.created_at, o.date_until, pm.name as payment_method FROM orders o INNER JOIN payment_methods pm ON o.payment_methods_id=pm.id";

if ($type == "riwayat") {
    $sql .= " WHERE o.date_until < NOW()";
} else {
    $sql .= " WHERE o.date_until >= NOW()";
}

$sql .= " AND o.users_username = ?";

$stmt = $conn->prepare($sql);
$stmt->bind_param('s',$username);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $data = [];
    while ($row = $result->fetch_assoc()) {
        $temp_data = [
            "created_at" => $row["created_at"],
            "date_until" => $row["date_until"],
            "payment_method" => $row["payment_method"],
        ];

        $sql2 = "SELECT  p.id, p.name, p.address, p.distance, p.pricepermonth, p.rating, p.main_photo, ".
                "CONCAT(u.firstname, ' ', u.lastname) as owner ".
                "FROM properties p INNER JOIN users u ON p.owner_id=u.username WHERE p.id=?";
        $stmt2 = $conn->prepare($sql2);
        $stmt2->bind_param('i', $row["properties_id"]);
        $stmt2->execute();
        $result2 = $stmt2->get_result();

        if ($result2->num_rows > 0) {
            while($row2 = $result2->fetch_assoc()) {
                $temp_data['property_data'] = $row2;
            }
        }
        array_push($data, $temp_data);
    }

    $arr = ["result" => "success", "message" => "Fetched order data.", "data" => $data];
} else if (!$conn->errno) {
    $arr = ["result" => "failed", "message" => "Something went wrong."];
} else {
    $arr = ["result" => "error", "message" => "sql error: $conn->error"];
}

$stmt->close();
echo json_encode($arr);