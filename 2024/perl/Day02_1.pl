#!perl
use warnings;
use strict;

open(INPUT, '<', "2024/input/day02.txt")
    or die $!;

my $safe = 0;

input:
while (<INPUT>) {
    my @levels = split /\s+/;

    my $dir = 0;
    my $i = 0;
    do {{
        my $diff = $levels[$i] - $levels[$i + 1];
        if ($diff == 0) {
            next input;
        }
        if (0 < $diff && $diff <= 3) {
            next input if ($dir < 0);
            $dir = 1;
        } elsif (-3 <= $diff && $diff < 0) {
            next input if ($dir > 0);
            $dir = -1;
        } else {
            next input;
        }
    }} while (++$i < $#levels - 1);

    $safe++;
}

print "Safe: $safe\n";
