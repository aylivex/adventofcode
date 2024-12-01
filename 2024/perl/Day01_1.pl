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

my @left_sorted_array = sort @left_array;
my @right_sorted_array = sort @right_array;

my $diff_sum = 0;
my $i = 0;
while ($i < $#left_sorted_array) {
    my $diff = abs($left_sorted_array[$i] - $right_sorted_array[$i]);
    $diff_sum += $diff;

    $i++;
}

print "Result: $diff_sum\n";
