#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/day01.txt")
    or die $!;

my @left_array = [];
my @right_array = [];

while (<INPUT>) {
    (my $left, my $right) = /(\d+)\s+(\d+)/;

    push @left_array, $left;
    push @right_array, $right;
}


my $similarity = 0;

for my $left (@left_array) {
    my $count = 0;
    for my $right (@right_array) {
        $count++ if $left == $right;
    }
    $similarity += ($left * $count);
}

print "Result: $similarity\n";
