from flask import Blueprint, render_template

analysen_bp = Blueprint('analysen', __name__)

@analysen_bp.route('/analysen')
def home():
    return render_template('analysen.html')
