#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/day03.txt")
    or die $!;

my $result = 0;
my $enabled = 1;

while (<INPUT>) {
    my @intructions = /(do\(\)|don't\(\)|mul\(\d{1,3},\d{1,3}\))/g;
    for my $instr (@intructions) {
        $enabled = 1 if $instr eq "do()";
        $enabled = 0 if $instr eq "don't()";
        if ($enabled) {
            if ($instr =~ /mul\((\d{1,3}),(\d{1,3})\)/) {
                $result += ($1 * $2);
            }
        }
    }
}

print "Result: $result\n";
