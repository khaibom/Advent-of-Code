<?php
$input = "199617-254904,7682367-7856444,17408-29412,963327-1033194,938910234-938964425,3207382-3304990,41-84,61624-105999,1767652-1918117,492-749,85-138,140-312,2134671254-2134761843,2-23,3173-5046,16114461-16235585,3333262094-3333392446,779370-814446,26-40,322284296-322362264,6841-12127,290497-323377,33360-53373,823429-900127,17753097-17904108,841813413-841862326,518858-577234,654979-674741,773-1229,2981707238-2981748769,383534-468118,587535-654644,1531-2363";

function get_ranges($input) {
    $ranges = explode(",", $input);
    foreach ($ranges as &$range){
        $range = explode("-", $range);
        $range[0] = (int)$range[0];
        $range[1] = (int)$range[1];
    }
    return $ranges;
}

function is_invalid($id) {
    $id = (string)$id;
    if(strlen($id)%2 == 1){
        return false;
    }
    for ($i = 0; $i < strlen($id)/2; $i++) {
        if($id[$i] != $id[strlen($id)/2+$i]) {
            return false;
        }
    }
    return true;
}


function get_sum_invalid_ids($start, $end) {
    $sum = 0;
    for($id = $start; $id <= $end; $id++){
        if(is_invalid($id)){
            $sum += $id;
        }
    }
    return $sum;
}

//main
$ranges = get_ranges($input);
$answer = 0;
foreach($ranges as $range){
    $answer += get_sum_invalid_ids($range[0], $range[1]);
}
print($answer);
//9188031749