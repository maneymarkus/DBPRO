library(gridExtra)
library(grid)
library(ggplot2)
library(lattice)
options(digits=3)
library(padr)
library(scales)
theme_set(theme_bw())

# read in original csv - all segments
s <- read.csv('~/My Shapes/Projects/Master/Module/3 _ SS 20/DBPRO - 6 LP/AnomalyDetection/a5_ohne_Duplikate_final_msdos.csv')
# read in csv containing all anomalies found on specific highway segment
r <- read.csv('~/My Shapes/Projects/Master/Module/3 _ SS 20/DBPRO - 6 LP/AnomalyDetection/output/target/ANOMALIES_150906120_e.csv')

# pick a specific time period
lim <- lims <- as.POSIXct(strptime(c("2017-12-23", "2018-01-04"), format = "%Y-%m-%d"))
# select preferd highway segement coresponting to the segment of the anomalie csv
seg <- '150906120'

# plot highway segment data (blue) + detected anomalies (red)
plotti <- ggplot(data=s[s$osm_id==seg,], aes(as.POSIXct(date_time), avg_vs, color='avg_speed')) +
   geom_line(col='blue') +
   theme(plot.title = element_text(size=14, face="bold"), ) +
   scale_x_datetime(breaks=date_breaks("1 day"), limits = lim) +
   theme(axis.text.x = element_text(angle = 45, hjust = 1) ) +
   geom_line(data=r[r$osm_id==seg,],aes(as.POSIXct(date_time), avg_vs, color='anomalies'), col='red') +
   labs(title=paste(' Segment:',seg), subtitle='Average speed (blue) and detected anomalies (red).', x='date_time', y='avg_speed')
print(plotti)


