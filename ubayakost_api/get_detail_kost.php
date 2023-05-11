<?php
require_once('env.php');

$conn = new mysqli($_ENV['SERVER_NAME'], $_ENV['USER_NAME'], $_ENV['USER_PASSWORD'], $_ENV['DATABASE_NAME']) or die("Connection error: " . $conn->error);

$arr = [];

extract($_POST);

$sql = "SELECT p.id, p.name, p.address, p.distance, p.pricepermonth, p.capacity, p.bathroom, p.rating, p.main_photo,".
        "t.name as types, bt.name as bathroom_types, CONCAT(u.firstname, ' ', u.lastname) as owner ".
        "FROM properties p INNER JOIN types t ON p.types_id=t.id INNER JOIN bathroom_types bt ON p.bathroom_types_id=bt.id INNER JOIN users u ON p.owner_id=u.username WHERE p.id=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param('i', $id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $data = [];
    while ($row = $result->fetch_assoc()) {
        $data = $row;
        $data["is_favorite"] = false;

        $sql2 = "SELECT * FROM favorite_properties WHERE users_username=? AND properties_id=?";
        $stmt2 = $conn->prepare($sql2);
        $stmt2->bind_param("si", $username, $row["id"]);
        $stmt2->execute();
        $result2 = $stmt2->get_result();
        
        if ($result2->num_rows > 0) {
            while($row2 = $result2->fetch_assoc()) {
                $data['is_favorite'] = true;
            }
        }
    }

    $arr = ["result" => "success", "message" => "Fetched properties data.", "data" => $data];
} else if (!$conn->errno) {
    $arr = ["result" => "failed", "message" => "Something went wrong."];
} else {
    $arr = ["result" => "error", "message" => "sql error: $conn->error"];
}

$stmt->close();
echo json_encode($arr);