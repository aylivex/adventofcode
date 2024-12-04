#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/day03.txt")
    or die $!;

my $result = 0;

while (<INPUT>) {
    my $enabled = 1;
    my @intructions = /(do\(\)|don't\(\)|mul\(\d{1,3},\d{1,3}\))/g;
    for my $instr (@intructions) {
        print "$instr";
        $enabled = 1 if $instr eq "do()";
        $enabled = 0 if $instr eq "don't()";
        print " - $enabled";
        if ($enabled) {
            if ($instr =~ /mul\((\d{1,3}),(\d{1,3})\)/) {
                $result += ($1 * $2);
                print " - $result ($1 * $2)";
            }
        }
        print "\n"
    }
}

print "Result: $result\n";
