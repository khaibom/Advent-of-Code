dial=50
answer=0
while read line; do
    direction="${line:0:1}"
    distance="${line:1}"

    if [ "$distance" -gt 100 ]; then
      distance=$((distance % 100))
    fi

    if [ "$direction" = "L" ]; then
      dial=$((dial - distance))
      if [ "$dial" -lt 0 ]; then
        dial=$((dial + 100))
      fi
    else
      dial=$((dial + distance))
      if [ "$dial" -ge 100 ]; then
        dial=$((dial - 100))
      fi
    fi

    if [ "$dial" -eq 0 ]; then
      answer=$((answer + 1))
    fi
done < input_1.txt

echo "$answer"
#1021