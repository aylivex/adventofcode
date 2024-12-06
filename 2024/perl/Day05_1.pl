#!perl
use warnings;
use strict;

open(INPUT, '<', "input.txt")
    or die $!;

my %reverse;
while (<INPUT>) {
    last if /^$/;
    (my $left, my $right) = /(\d+)\|(\d+)/;
    if (defined $reverse{$right}) {
        push @{$reverse{$right}}, $left;
    } else {
        $reverse{$right} = [ $left ];
    }
}

my @pages;
while (<INPUT>) {
    chop;
    my @page_list = split /,/;
    push @pages, \@page_list;
}

my $middle_sum = 0;

for my $update (@pages) {
    my @update_pages = @{$update};
    
    my $valid = 1;

    my $i = $#update_pages;
    while ($i > 0 && $valid) {
        my $left = $update_pages[$i];
        my $j = $i - 1;
        while ($j >= 0 && $valid) {
            my $right = $update_pages[$j];
            if (defined $reverse{$right}) {
                for my $page (@{$reverse{$right}}) {
                    if ($page == $left) {
                        $valid = 0;
                        last;
                    }
                }
            }
            --$j;
        }
        --$i;
    }

    if ($valid) {
        my $middle = $update_pages[($#update_pages + 1) / 2];
        $middle_sum += $middle;
    }
}

print "Answer: $middle_sum\n";
