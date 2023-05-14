document.getElementById('confirmModal').addEventListener('show.bs.modal', event => {
  const fileId = event.relatedTarget.dataset.fileId;
  event.target.querySelector('#modal-data').value = fileId;
});

document.getElementById('upload').addEventListener('submit', () => {
  document.getElementById('upload-spinner').hidden = false;
  document.getElementById('upload-button').disabled = true;
});