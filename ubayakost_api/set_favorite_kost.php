<?php
$conn = new mysqli("localhost", "root", "mysql", "anmp_160420016") or die("Connection error: ".$conn->error);

$arr = [];

extract($_POST);

$sql = "SELECT * FROM favorite_properties WHERE users_username=? AND properties_id=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("si", $username, $properties_id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $sql2 = "DELETE FROM favorite_properties WHERE users_username=? AND properties_id=?";
    $stmt = $conn->prepare($sql2);
    $stmt->bind_param("si", $username, $properties_id);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        $arr = ["result" => "success"];
    } else {
        $arr = ["result" => "error", "message" => "sql error: $conn->error"];
    }
} else {
    $sql2 = "INSERT favorite_properties(users_username, properties_id) VALUES(?, ?)";
    $stmt = $conn->prepare($sql2);
    $stmt->bind_param("si", $username, $properties_id);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        $arr = ["result" => "success"];
    } else {
        $arr = ["result" => "error", "message" => "sql error: $conn->error"];
    }
}

$stmt->close();
echo json_encode($arr);