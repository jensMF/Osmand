package net.osmand.data;

import net.osmand.util.MapUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransportStop extends MapObject {

	private static final int DELETED_STOP = -1;

	private int[] referencesToRoutes = null;
	private TransportStopAggregated transportStopAggregated;
	public int distance;
	public int x31;
	public int y31;
	private List<TransportStopExit> exits;

	public TransportStop() {
	}

	public int[] getReferencesToRoutes() {
		return referencesToRoutes;
	}

	public void setReferencesToRoutes(int[] referencesToRoutes) {
		this.referencesToRoutes = referencesToRoutes;
	}

	public boolean isDeleted() {
		return referencesToRoutes != null && referencesToRoutes.length == 1 && referencesToRoutes[0] == DELETED_STOP;
	}

	public boolean hasReferencesToRoutes() {
		return referencesToRoutes != null && referencesToRoutes.length > 0;
	}

	public void setDeleted() {
		this.referencesToRoutes = new int[] { DELETED_STOP };
	}

	public Amenity getAmenity() {
		if (transportStopAggregated != null) {
			return transportStopAggregated.getAmenity();
		}
		return null;
	}

	public void setAmenity(Amenity amenity) {
		if (transportStopAggregated == null) {
			transportStopAggregated = new TransportStopAggregated();
		}
		transportStopAggregated.setAmenity(amenity);
	}

	public List<TransportStop> getLocalTransportStops() {
		if (transportStopAggregated != null) {
			return transportStopAggregated.getLocalTransportStops();
		}
		return Collections.emptyList();
	}

	public void addLocalTransportStop(TransportStop stop) {
		if (transportStopAggregated == null) {
			transportStopAggregated = new TransportStopAggregated();
		}
		transportStopAggregated.addLocalTransportStop(stop);
	}

	public List<TransportStop> getNearbyTransportStops() {
		if (transportStopAggregated != null) {
			return transportStopAggregated.getNearbyTransportStops();
		}
		return Collections.emptyList();
	}

	public void addNearbyTransportStop(TransportStop stop) {
		if (transportStopAggregated == null) {
			transportStopAggregated = new TransportStopAggregated();
		}
		transportStopAggregated.addNearbyTransportStop(stop);
	}

	public TransportStopAggregated getTransportStopAggregated() {
		return transportStopAggregated;
	}

	public void setTransportStopAggregated(TransportStopAggregated stopAggregated) {
		transportStopAggregated = stopAggregated;
	}

	@Override
	public void setLocation(double latitude, double longitude) {
		super.setLocation(latitude, longitude);
	}

	public void setLocation(int zoom, int dx, int dy) {
		x31 = dx << (31 - zoom);
		y31 = dy << (31 - zoom);
		setLocation(MapUtils.getLatitudeFromTile(zoom, dy), MapUtils.getLongitudeFromTile(zoom, dx));
	}

	public void addExit(TransportStopExit transportStopExit) {
		if (exits == null) {
			exits = new ArrayList<>();
		}
		exits.add(transportStopExit);
	}

	public List<TransportStopExit> getExits() {
		if (exits == null) {
			return Collections.emptyList();
		}
		return this.exits;
	}

	public String getExitsString() {
		String exitsString = "";
		String refString = "";
		if (this.exits != null) {
			int i = 1;
			exitsString = exitsString + " Exits: [";
			for (TransportStopExit e : this.exits) {
				if (e.getRef() != null) {
					refString = " [ref:" + e.getRef() + "] ";
				}
				exitsString = exitsString + " " + i + ")" + refString + e.getName() + " " + e.getLocation() + " ]";
				i++;
			}
		}
		return exitsString;
	}

	public boolean compareStop(TransportStop thatObj) {
		if (this.compareObject(thatObj) &&
				((this.referencesToRoutes == null && thatObj.referencesToRoutes == null) ||
						(this.referencesToRoutes != null && thatObj.referencesToRoutes != null && this.referencesToRoutes.length == thatObj.referencesToRoutes.length)) &&
				((this.exits == null && thatObj.exits == null) ||
						(this.exits != null && thatObj.exits != null && this.exits.size() == thatObj.exits.size()))) {
			if (this.exits != null) {
				for (int i = 0; i < this.exits.size(); i++) {
					if (!this.exits.get(i).compareExit(thatObj.exits.get(i))) {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}
}
