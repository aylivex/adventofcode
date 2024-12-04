#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/day03.txt")
    or die $!;

my $result = 0;

while (<INPUT>) {
    while (/mul\((\d{1,3}),(\d{1,3})\)/g) {
        $result += $1 * $2;
    }
}

print "Result: $result\n";
